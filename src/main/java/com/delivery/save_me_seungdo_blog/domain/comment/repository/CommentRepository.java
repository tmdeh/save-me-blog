package com.delivery.save_me_seungdo_blog.domain.comment.repository;

import com.delivery.save_me_seungdo_blog.domain.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}