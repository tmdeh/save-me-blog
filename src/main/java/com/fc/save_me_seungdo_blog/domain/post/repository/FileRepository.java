package com.fc.save_me_seungdo_blog.domain.post.repository;

import com.fc.save_me_seungdo_blog.domain.post.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}