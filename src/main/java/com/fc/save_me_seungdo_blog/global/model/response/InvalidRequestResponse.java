package com.fc.save_me_seungdo_blog.global.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidRequestResponse {
    private String message;
    private String target;
}
