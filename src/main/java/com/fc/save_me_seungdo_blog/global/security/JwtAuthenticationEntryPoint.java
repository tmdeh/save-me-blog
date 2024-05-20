package com.fc.save_me_seungdo_blog.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fc.save_me_seungdo_blog.global.exception.code.AuthErrorCode;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {


        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String responseBody = objectMapper.writeValueAsString(Api.EXCEPTION(new CustomApiException(AuthErrorCode.INVALID_TOKEN)));

        // 응답 전송
        response.getWriter().write(responseBody);

    }
}
