package com.fc.save_me_seungdo_blog.domain.comment.controller;

import com.fc.save_me_seungdo_blog.domain.comment.model.request.CreateCommentRequest;
import com.fc.save_me_seungdo_blog.domain.comment.service.CommentService;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<Api<?>> create(
        @PathVariable
        Long postId,
        @Valid
        @RequestBody
        CreateCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(postId, request));
    }

}