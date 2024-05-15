package com.delivery.save_me_seungdo_blog.domain.auth.repository;

import com.delivery.save_me_seungdo_blog.domain.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}