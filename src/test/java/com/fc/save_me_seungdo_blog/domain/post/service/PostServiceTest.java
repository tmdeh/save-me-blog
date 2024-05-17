package com.fc.save_me_seungdo_blog.domain.post.service;


import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import com.fc.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.fc.save_me_seungdo_blog.domain.post.repository.PostRepository;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;


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
        assertThat(response.getResult().getCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getBody().getTitle()).isEqualTo(title);
        assertThat(response.getBody().getContent()).isEqualTo(content);
    }
}