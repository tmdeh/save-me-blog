package com.delivery.save_me_seungdo_blog.global.exception;

import com.delivery.save_me_seungdo_blog.global.exception.type.ErrorCode;
import org.springframework.web.client.HttpStatusCodeException;

public class CustomApiException extends HttpStatusCodeException {

    private final ErrorCode errorCode;

    public CustomApiException(ErrorCode errorCode) {
        super(errorCode.getStatus());
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorCode.getMessage();
    }

}
