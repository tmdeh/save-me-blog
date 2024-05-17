package com.delivery.save_me_seungdo_blog.domain.post.controller;

import com.delivery.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.delivery.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.delivery.save_me_seungdo_blog.domain.post.service.PostService;
import com.delivery.save_me_seungdo_blog.global.model.response.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Api<PostResponse>> create(
            @Valid
            @RequestBody
            CreatePostRequest createPostRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(createPostRequest));
    }

}
