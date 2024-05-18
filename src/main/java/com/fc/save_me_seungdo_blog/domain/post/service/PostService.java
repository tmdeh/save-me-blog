package com.fc.save_me_seungdo_blog.domain.post.service;

import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import com.fc.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.GetPostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.fc.save_me_seungdo_blog.domain.post.repository.PostRepository;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public Api<Page<PostResponse>> getList(GetPostRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
            Sort.by(Sort.Direction.fromString(request.getDirection().name()),
                request.getSortBy().name()));

        return Api.OK(postRepository.findAll(pageable).map(post -> PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .build()));

    }
}
