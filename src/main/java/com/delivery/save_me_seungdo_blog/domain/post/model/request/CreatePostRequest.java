package com.delivery.save_me_seungdo_blog.domain.post.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;

}
