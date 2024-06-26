package com.fc.save_me_seungdo_blog.domain.post.model.response;

import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;

    public static PostResponse toResponse(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .build();
    }
}
