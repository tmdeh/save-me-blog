package com.fc.save_me_seungdo_blog.domain.post.repository;

import com.fc.save_me_seungdo_blog.domain.post.model.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByTitleContaining(String title, Pageable pageable);
    Optional<Post> findByIdAndUserEmail(Long id, String email);
}