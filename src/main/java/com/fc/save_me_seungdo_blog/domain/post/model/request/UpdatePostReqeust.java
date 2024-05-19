package com.fc.save_me_seungdo_blog.domain.post.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePostReqeust {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
