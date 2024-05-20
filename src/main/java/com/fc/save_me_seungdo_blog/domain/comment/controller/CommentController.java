package com.fc.save_me_seungdo_blog.domain.comment.controller;

import com.fc.save_me_seungdo_blog.domain.comment.model.request.CreateCommentRequest;
import com.fc.save_me_seungdo_blog.domain.comment.model.request.UpdateCommentRequest;
import com.fc.save_me_seungdo_blog.domain.comment.model.response.CommentResponse;
import com.fc.save_me_seungdo_blog.domain.comment.service.CommentService;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Api<CommentResponse>> create(
        @PathVariable
        Long postId,
        @Valid
        @RequestBody
        CreateCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(postId, request));
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<Api<List<CommentResponse>>> get(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.readAll(postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Api<CommentResponse>> update(
        @PathVariable
        Long commentId,
        @Valid
        @RequestBody
        UpdateCommentRequest reqeust
    ) {
        return ResponseEntity.ok(commentService.update(commentId, reqeust));
    }

}
