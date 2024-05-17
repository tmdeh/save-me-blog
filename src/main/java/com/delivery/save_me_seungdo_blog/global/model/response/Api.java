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

    // 커스텀 응답
    public static <T> Api<T> SUCCESS(HttpStatus status, T body) {
        Result result = new Result(status.value(), status.name());
        return new Api<>(result, body);
    }

    // body가 있는 성공 응답
    public static <T> Api<T> OK(T body) {
        Result result = new Result(HttpStatus.OK.value(), HttpStatus.OK.name());
        return new Api<>(result, body);
    }

    // body가 없는 성공 응답
    public static Api<?> OK() {
        Result result = new Result(HttpStatus.OK.value(), "OK");
        return new Api<>(result, null);
    }


    // 예외 응답
    public static Api<?> EXCEPTION(CustomApiException exception) {
        Result result = new Result(exception.getStatusCode().value(), exception.getMessage());
        return new Api<>(result, null);
    }

    // 서버 에러 응답
    public static Api<?> ERROR(Exception e) {
        Result result = new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new Api<>(result, null);
    }

}
