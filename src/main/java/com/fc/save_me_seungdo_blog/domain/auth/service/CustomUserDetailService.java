package com.fc.save_me_seungdo_blog.domain.auth.service;

import com.fc.save_me_seungdo_blog.domain.auth.model.dto.CustomUserDetails;
import com.fc.save_me_seungdo_blog.domain.auth.model.entity.User;
import com.fc.save_me_seungdo_blog.domain.auth.repository.UserRepository;
import com.fc.save_me_seungdo_blog.global.exception.CustomApiException;
import com.fc.save_me_seungdo_blog.global.exception.code.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomApiException(AuthErrorCode.NOT_FOUND));
        return new CustomUserDetails(user);
    }
}
