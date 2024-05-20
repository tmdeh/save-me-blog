package com.fc.save_me_seungdo_blog.domain.auth.service;

import com.fc.save_me_seungdo_blog.domain.auth.model.entity.User;
import com.fc.save_me_seungdo_blog.domain.auth.model.request.SignUpRequest;
import com.fc.save_me_seungdo_blog.domain.auth.model.response.UserResponse;
import com.fc.save_me_seungdo_blog.domain.auth.repository.UserRepository;
import com.fc.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fc.save_me_seungdo_blog.global.exception.code.AuthErrorCode;
import com.fc.save_me_seungdo_blog.global.model.response.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Api<UserResponse> signUp(SignUpRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new CustomApiException(AuthErrorCode.EXIST);
        }

        String password = bCryptPasswordEncoder.encode(request.getPassword());

        User user = userRepository.save(User.builder()
            .email(request.getEmail())
            .password(password)
            .name(request.getName())
            .build());

         return Api.SUCCESS(HttpStatus.CREATED, UserResponse.toResponse(user));
    }
}
