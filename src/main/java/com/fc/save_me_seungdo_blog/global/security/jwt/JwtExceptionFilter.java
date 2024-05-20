package com.fc.save_me_seungdo_blog.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fc.save_me_seungdo_blog.global.exception.code.AuthErrorCode;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        response.setContentType("application/json; charset=utf-8");

        String responseBody = "";

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseBody = objectMapper.writeValueAsString(Api.EXCEPTION(new CustomApiException(
                AuthErrorCode.EXPIRED_TOKEN)));
        } catch (SignatureException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseBody = objectMapper.writeValueAsString(Api.EXCEPTION(new CustomApiException(AuthErrorCode.INVALID_TOKEN)));
        } catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            responseBody = objectMapper.writeValueAsString(Api.EXCEPTION(e));
        }

        response.getWriter().write(responseBody);

    }
}
