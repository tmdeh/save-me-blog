package com.fc.save_me_seungdo_blog.domain.auth.controller;

import com.fc.save_me_seungdo_blog.domain.auth.model.request.SignUpRequest;
import com.fc.save_me_seungdo_blog.domain.auth.service.AuthService;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Api<?>> login(
        @Valid
        @RequestBody
        SignUpRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(request));
    }
}
