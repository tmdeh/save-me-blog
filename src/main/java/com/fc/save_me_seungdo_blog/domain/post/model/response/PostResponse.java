package com.fc.save_me_seungdo_blog.domain.post.model.response;

import com.fc.save_me_seungdo_blog.domain.comment.model.response.CommentResponse;
import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import java.util.List;
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
    private List<CommentResponse> comments;

    public static PostResponse toResponse(Post post) {
        List<CommentResponse> comments = post.getComments().stream().map(CommentResponse::toResponse)
            .toList();

        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .comments(comments)
            .build();
    }

}
