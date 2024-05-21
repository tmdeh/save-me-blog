package com.fc.save_me_seungdo_blog.domain.post.controller;

import com.fc.save_me_seungdo_blog.domain.post.model.request.CreatePostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.GetPostRequest;
import com.fc.save_me_seungdo_blog.domain.post.model.request.UpdatePostReqeust;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostDetailResponse;
import com.fc.save_me_seungdo_blog.domain.post.model.response.PostResponse;
import com.fc.save_me_seungdo_blog.domain.post.service.PostService;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Api<PostDetailResponse>> create(
        @Valid
        @RequestBody
        CreatePostRequest createPostRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(postService.create(createPostRequest));
    }

    @GetMapping
    public ResponseEntity<Api<Page<PostResponse>>> getAll(
        @Valid
        @ModelAttribute
        GetPostRequest getPostRequest
    ) {
        return ResponseEntity.ok(postService.getList(getPostRequest));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Api<PostDetailResponse>> get(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getDetail(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Api<PostResponse>> update(
        @PathVariable Long postId,
        @Valid
        @RequestBody
        UpdatePostReqeust reqeust
    ) {
        return ResponseEntity.ok(postService.update(postId, reqeust));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Api<?>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(postService.delete(id));
    }


    @PostMapping("/{postId}/file")
    public ResponseEntity<Api<?>> fileUpload(
        @RequestPart MultipartFile[] files,
        @PathVariable Long postId) {

        return ResponseEntity.ok(postService.fileUpload(postId, files));
    }

}
