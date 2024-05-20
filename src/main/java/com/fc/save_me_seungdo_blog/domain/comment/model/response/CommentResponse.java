package com.fc.save_me_seungdo_blog.domain.comment.model.response;

import com.fc.save_me_seungdo_blog.domain.auth.model.response.UserResponse;
import com.fc.save_me_seungdo_blog.domain.comment.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private UserResponse user;
    private String content;

    public static CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .user(UserResponse.toResponse(comment.getUser()))
            .build();
    }
}
