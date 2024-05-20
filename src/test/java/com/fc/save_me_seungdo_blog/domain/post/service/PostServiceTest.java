package com.fc.save_me_seungdo_blog.domain.post.service;


import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import com.fc.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.GetPostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.UpdatePostReqeust;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostDetailResponse;
import com.fc.save_me_seungdo_blog.domain.post.model.type.DirectionEnum;
import com.fc.save_me_seungdo_blog.domain.post.model.type.SortByEnum;
import com.fc.save_me_seungdo_blog.domain.post.repository.PostRepository;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;


    @Test
    @DisplayName("블로그 등록하기")
    public void testCreatePost() {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용";

        CreatePostRequest request = new CreatePostRequest(title, content);
        when(postRepository.save(Mockito.any(Post.class))).thenReturn(new Post(0L, title, content));

        // when
        Api<PostDetailResponse> response = postService.create(request);

        // then
        verify(postRepository, times(1)).save(Mockito.any(Post.class));
        assertThat(response.getResult().getCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getBody().getTitle()).isEqualTo(title);
        assertThat(response.getBody().getContent()).isEqualTo(content);

    }

    @Test
    @DisplayName("블로그 전체 조회하기")
    public void testReadALl() {
        // given

        final int size = 5;
        final int postCount = 20;
        final int pageCount = (postCount + size - 1) / size;



        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < postCount; i++) {
            posts.add(new Post((long) i, "Title" + i, "Content" + i));
        }


        for (int page = 0; page < pageCount; page++) {

            int start = page * size;
            int end = Math.min(start + size, postCount);

            GetPostRequest request = new GetPostRequest(page, size, SortByEnum.id, DirectionEnum.asc, "");

            PageRequest pageable = PageRequest.of(page, size, Sort.by(DirectionEnum.asc.getValue(), SortByEnum.id.name()));
            Page<Post> pageImpl = new PageImpl<>(posts.subList(start, end), pageable, postCount);

            when(postRepository.findAllByTitleContaining("", pageable)).thenReturn(pageImpl);

            // when
            Api<Page<PostDetailResponse>> result = postService.getList(request);

            // then
            assertThat(result.getResult().getCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getBody().getTotalElements()).isEqualTo(postCount);
            assertThat(result.getBody().getTotalPages()).isEqualTo(pageCount);
            assertThat(result.getBody().getContent().size()).isEqualTo(end - start);

            List<PostDetailResponse> responses = result.getBody().getContent();

            for (int i = 0; i < responses.size(); i++) {
                PostDetailResponse response = responses.get(i);
                Post originalPost = posts.get(start + i);
                assertThat(response.getId()).isEqualTo(originalPost.getId());
                assertThat(response.getTitle()).isEqualTo(originalPost.getTitle());
                assertThat(response.getContent()).isEqualTo(originalPost.getContent());
            }
            verify(postRepository, times(1)).findAllByTitleContaining("", pageable);
        }
    }

    @Test
    @DisplayName("블로그 검색하기")
    public void testSearch() {
        // given
        final int size = 5;
        final int postCount = 5;
        final String targetStr = "hello";


        GetPostRequest request = new GetPostRequest(0, size, SortByEnum.id, DirectionEnum.asc, targetStr);
        PageRequest pageable = PageRequest.of(0, size, Sort.by(DirectionEnum.asc.getValue(), SortByEnum.id.name()));

        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < postCount; i++) {
            if(i % 2 == 0) {
                posts.add(new Post((long) i, targetStr + "Title" + i, "Content" + i));
                continue;
            }
            posts.add(new Post((long) i, "Title" + i, "Content" + i));
        }


        List<Post> targets = new ArrayList<>();
        for (int i = 0; i < postCount; i+=2) {
            targets.add(posts.get(i));
        }


        when(postRepository.findAllByTitleContaining(targetStr, pageable)).thenReturn(new PageImpl<>(targets));

        // when
        Api<Page<PostDetailResponse>> result = postService.getList(request);


        // then
        verify(postRepository, times(1)).findAllByTitleContaining(targetStr, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getResult().getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getBody().getTotalElements()).isEqualTo(targets.size());

        for(int i = 0; i < result.getBody().getContent().size(); i++) {
            String responseTitle = result.getBody().getContent().get(i).getTitle();
            String targetTitle = targets.get(i).getTitle();

            assertThat(responseTitle).isEqualTo(targetTitle);
        }
    }

    @Test
    @DisplayName("블로그 업데이트하기")
    public void testUpdate() {
        // given
        long id = 1L;
        String oldTitle = "title";
        String oldContent = "content";

        String newTitle = "newTitle";
        String newContent = "newContent";

        UpdatePostReqeust request = new UpdatePostReqeust(id, newTitle, newContent);
        Post post = new Post(id, oldTitle, oldContent);
        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        // when
        Api<PostDetailResponse> result = postService.update(request);

        // then
        verify(postRepository).findById(id);
        assertThat(result).isNotNull();
        assertThat(result.getResult().getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(id);
        assertThat(result.getBody().getTitle()).isEqualTo(newTitle);
        assertThat(result.getBody().getContent()).isEqualTo(newContent);
    }


    @Test
    @DisplayName("블로그 삭제하기")
    public void testDelete() {
        // given
        long id = 1L;
        String title = "title";
        String content = "content";

        Post post = new Post(id, title, content);
        when(postRepository.findById(id)).thenReturn(Optional.of(post));


        // when
        Api<?> result = postService.delete(id);

        // then
        verify(postRepository).delete(post);
        assertThat(result).isNotNull();
        assertThat(result.getResult().getCode()).isEqualTo(HttpStatus.OK.value());
    }

}