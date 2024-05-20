package com.fc.save_me_seungdo_blog.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글은 없습니다."),
    ;

    private final HttpStatus status;
    private final String errorMessage;

}
