package com.fc.save_me_seungdo_blog.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements ErrorCode{

    NOT_FOUND(HttpStatus.NOT_FOUND, "없는 블로그입니다."),
    ;

    private final HttpStatus status;
    private final String errorMessage;
}
