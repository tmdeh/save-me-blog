package com.fc.save_me_seungdo_blog.domain.post.controller;

import com.fc.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.GetPostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.fc.save_me_seungdo_blog.domain.post.service.PostService;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import jakarta.validation.Valid;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<Api<List<PostResponse>>> getAll(
        @Valid
        @ModelAttribute
        GetPostRequest getPostRequest
    ) {
        // TODO: enum 예외 처리
        return ResponseEntity.ok(postService.getList(getPostRequest));
    }

}
