package com.fc.save_me_seungdo_blog.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FileErrorCode implements ErrorCode{
    FILE_SAVE_FAILED(HttpStatus.BAD_REQUEST, "파일 저장에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String errorMessage;
}
