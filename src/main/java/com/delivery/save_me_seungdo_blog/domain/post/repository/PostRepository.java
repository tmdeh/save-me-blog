package com.delivery.save_me_seungdo_blog.domain.post.repository;

import com.delivery.save_me_seungdo_blog.domain.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}