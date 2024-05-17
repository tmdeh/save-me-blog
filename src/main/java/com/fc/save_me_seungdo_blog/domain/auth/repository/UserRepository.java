package com.fc.save_me_seungdo_blog.domain.auth.repository;

import com.fc.save_me_seungdo_blog.domain.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}