package com.fc.save_me_seungdo_blog.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자 입니다."),
    ;

    private final HttpStatus status;
    private final String errorMessage;
}
