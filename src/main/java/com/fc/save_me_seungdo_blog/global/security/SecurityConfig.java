package com.fc.save_me_seungdo_blog.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.save_me_seungdo_blog.global.security.jwt.JwtExceptionFilter;
import com.fc.save_me_seungdo_blog.global.security.jwt.JwtFilter;
import com.fc.save_me_seungdo_blog.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login", "/sign-up", "/v3/api-docs/**", "/swagger-ui/**",
                    "/swagger-resources/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/post").permitAll()
                .anyRequest().authenticated())
            .addFilterAt(
                new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
            .addFilterBefore(new JwtExceptionFilter(objectMapper), JwtFilter.class)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {

        return configuration.getAuthenticationManager();
    }
}
