package com.delivery.save_me_seungdo_blog.global.model.response;

import com.delivery.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Api<T> {

    private Result result;
    private T body;

    public Api(CustomApiException e) {
        this.result = new Result(e.getStatusCode().value(), e.getErrorMessage());
        this.body = null;
    }

    public Api(Exception e) {
        this.result = new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        this.body = null;
    }

}
