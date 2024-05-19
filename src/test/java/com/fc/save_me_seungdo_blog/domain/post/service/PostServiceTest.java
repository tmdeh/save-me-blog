package com.fc.save_me_seungdo_blog.domain.post.service;


import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import com.fc.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.GetPostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.fc.save_me_seungdo_blog.domain.post.model.type.DirectionEnum;
import com.fc.save_me_seungdo_blog.domain.post.model.type.SortByEnum;
import com.fc.save_me_seungdo_blog.domain.post.repository.PostRepository;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;


    @Test
    @DisplayName("등록 성공")
    public void testCreatePost() {
        // given
        String title = "테스트 제목";
        String content = "테스트 내용";

        CreatePostRequest request = new CreatePostRequest(title, content);
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(new Post(0L, title, content));

        // when
        Api<PostResponse> response = postService.create(request);

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
            PageImpl<Post> pageImpl = new PageImpl<>(posts.subList(start, end), pageable, postCount);

            Mockito.when(postRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(pageImpl);

            // when
            Api<Page<PostResponse>> result = postService.getList(request);

            // then
            assertThat(result.getResult().getCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getBody().getTotalElements()).isEqualTo(postCount);
            assertThat(result.getBody().getTotalPages()).isEqualTo(pageCount);
            assertThat(result.getBody().getContent().size()).isEqualTo(end - start);

            List<PostResponse> responses = result.getBody().getContent();

            for (int i = 0; i < responses.size(); i++) {
                PostResponse response = responses.get(i);
                Post originalPost = posts.get(start + i);
                assertThat(response.getId()).isEqualTo(originalPost.getId());
                assertThat(response.getTitle()).isEqualTo(originalPost.getTitle());
                assertThat(response.getContent()).isEqualTo(originalPost.getContent());
            }
            verify(postRepository, times(1)).findAll(ArgumentMatchers.eq(pageable));
        }
    }

    @Test
    @DisplayName("블로그 검색하기")
    public void testSearch() {
        // given

        // when

        // then
    }
}