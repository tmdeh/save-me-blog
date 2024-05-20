package com.fc.save_me_seungdo_blog.domain.comment.model.response;

import com.fc.save_me_seungdo_blog.domain.auth.model.response.UserResponse;
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
}
