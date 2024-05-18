package com.fc.save_me_seungdo_blog.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RequestErrorCode implements ErrorCode{
    TYPE_MISS_MATCH(HttpStatus.BAD_REQUEST, "잘못된 파라미터 입니다."),
    ;
    private final HttpStatus status;
    private final String errorMessage;
}
