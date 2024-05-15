package com.delivery.save_me_seungdo_blog.global.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Api<T> {

    private Result result;

    private T body;
}
