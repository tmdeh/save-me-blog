package com.delivery.save_me_seungdo_blog.global.model.response;

import com.delivery.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Api<T> {

    private Result result;

    private T body;

    // 성공하고 body가 있는 경우
    public static <T> Api<T> SUCCESS(HttpStatusCode code, T body) {
        Result result = new Result(code.value(), "성공");
        return new Api<>(result, body);
    }

    // body만 있는 경우
    public static <T> Api<T> OK(T body) {
        Result result = new Result(HttpStatus.OK.value(), "성공");
        return new Api<>(result, body);
    }

    // body가 없는 경우
    public static Api <Object>OK() {
        Result result = new Result(HttpStatus.OK.value(), "성공");
        return new Api<>(result, null);
    }

    // 예외인 경우
    public static Api<Object> EXCEPTION(CustomApiException exception) {
        Result result = new Result(exception.getStatusCode().value(), exception.getErrorMessage());
        return new Api<>(result, null);
    }


    // 서버 에러인 경우
    public static Api<Object> ERROR(Exception exception) {
        Result result = new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return new Api<>(result, null);
    }
}
