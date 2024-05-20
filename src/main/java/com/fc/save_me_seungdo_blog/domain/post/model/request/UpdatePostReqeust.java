package com.fc.save_me_seungdo_blog.domain.post.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePostReqeust {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
