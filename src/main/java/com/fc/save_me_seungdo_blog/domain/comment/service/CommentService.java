package com.fc.save_me_seungdo_blog.domain.comment.service;

import com.fc.save_me_seungdo_blog.domain.auth.model.entity.User;
import com.fc.save_me_seungdo_blog.domain.auth.model.response.UserResponse;
import com.fc.save_me_seungdo_blog.domain.auth.repository.UserRepository;
import com.fc.save_me_seungdo_blog.domain.comment.model.entity.Comment;
import com.fc.save_me_seungdo_blog.domain.comment.model.request.CreateCommentRequest;
import com.fc.save_me_seungdo_blog.domain.comment.model.request.UpdateCommentRequest;
import com.fc.save_me_seungdo_blog.domain.comment.model.response.CommentResponse;
import com.fc.save_me_seungdo_blog.domain.comment.repository.CommentRepository;
import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import com.fc.save_me_seungdo_blog.domain.post.repository.PostRepository;
import com.fc.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fc.save_me_seungdo_blog.global.exception.code.AuthErrorCode;
import com.fc.save_me_seungdo_blog.global.exception.code.CommentErrorCode;
import com.fc.save_me_seungdo_blog.global.exception.code.PostErrorCode;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Api<CommentResponse> create(Long postId, CreateCommentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomApiException(AuthErrorCode.NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() ->
            new CustomApiException(PostErrorCode.NOT_FOUND));

        Comment comment = commentRepository.save(Comment.builder()
            .user(user)
            .post(post)
            .content(request.getContent())
            .build());

        return Api.SUCCESS(HttpStatus.CREATED, CommentResponse.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .user(UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build()).build());
    }


    @Transactional
    public Api<CommentResponse> update(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
            new CustomApiException(CommentErrorCode.NOT_FOUND));

        comment.updateContent(request.getContent());

        return Api.OK(CommentResponse.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .user(UserResponse.builder()
                .id(comment.getUser().getId())
                .name(comment.getUser().getName())
                .email(comment.getUser().getEmail())
                .build())
            .build());
    }

}
