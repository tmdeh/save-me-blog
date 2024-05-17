package com.delivery.save_me_seungdo_blog.global.exception;

import com.delivery.save_me_seungdo_blog.global.model.response.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<Api<?>> customApiException(CustomApiException e) {
        log.warn(e.getErrorMessage());
        return ResponseEntity.status(e.getStatusCode()).body(Api.EXCEPTION(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Api<?>> exception(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Api.ERROR(e));
    }

}
