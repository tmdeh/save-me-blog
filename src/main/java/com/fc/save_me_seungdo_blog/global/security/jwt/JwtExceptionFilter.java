package com.fc.save_me_seungdo_blog.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fc.save_me_seungdo_blog.global.exception.code.AuthErrorCode;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public JwtExceptionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex){
            handleException(response, new CustomApiException(AuthErrorCode.EXPIRED_TOKEN));
        } catch (SignatureException ex) {
            handleException(response, new CustomApiException(AuthErrorCode.INVALID_TOKEN));
        } catch (AuthenticationException ex) {
            handleException(response, new CustomApiException(AuthErrorCode.UNAUTHORIZED));
        }
    }

    private void handleException(HttpServletResponse response, CustomApiException ex) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        String responseBody = objectMapper.writeValueAsString(Api.EXCEPTION(ex));
        response.getWriter().write(responseBody);
    }
}

