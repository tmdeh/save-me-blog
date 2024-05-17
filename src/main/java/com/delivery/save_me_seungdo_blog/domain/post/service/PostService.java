package com.delivery.save_me_seungdo_blog.domain.post.service;

import com.delivery.save_me_seungdo_blog.domain.post.model.entity.Post;
import com.delivery.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.delivery.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.delivery.save_me_seungdo_blog.domain.post.repository.PostRepository;
import com.delivery.save_me_seungdo_blog.global.model.response.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Api<PostResponse> create(CreatePostRequest request) {
        Post post = postRepository.save(
                Post.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build()
        );

        return Api.SUCCESS(HttpStatus.CREATED, PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build()
        );
    }

}
