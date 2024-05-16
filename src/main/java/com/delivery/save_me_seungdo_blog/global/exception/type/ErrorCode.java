package com.delivery.save_me_seungdo_blog.global.exception.type;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();
    String getMessage();
}
