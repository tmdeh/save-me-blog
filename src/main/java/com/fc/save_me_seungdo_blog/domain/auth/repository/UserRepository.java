package com.fc.save_me_seungdo_blog.domain.auth.repository;

import com.fc.save_me_seungdo_blog.domain.auth.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}