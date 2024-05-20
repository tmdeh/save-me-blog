package com.fc.save_me_seungdo_blog.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.save_me_seungdo_blog.domain.auth.model.dto.CustomUserDetails;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import com.fc.save_me_seungdo_blog.global.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

//        String token = jwtUtil.createJwt(username, role, 24 * 60 * 60 * 1000L); // 24시간
        String token = jwtUtil.createJwt(username, role, 0L); // 24시간

        response.addHeader("Authorization", "Bearer " + token);


        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        String responseBody = objectMapper.writeValueAsString(Api.OK());

        // 응답 전송
        response.getWriter().write(responseBody);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
        throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String responseBody = objectMapper.writeValueAsString(Api.EXCEPTION(failed));

        // 응답 전송
        response.getWriter().write(responseBody);
    }

}
