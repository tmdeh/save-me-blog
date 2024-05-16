package com.delivery.save_me_seungdo_blog.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TestErrorCode implements ErrorCode{

    TEST_ERROR_CODE("테스트 예외입니다.",  HttpStatus.BAD_REQUEST)
    ;
    private final String message;
    private final HttpStatus status;
}
