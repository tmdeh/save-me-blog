package com.fc.save_me_seungdo_blog.domain.post.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePostReqeust {
    private Long id;
    private String title;
    private String content;
}
