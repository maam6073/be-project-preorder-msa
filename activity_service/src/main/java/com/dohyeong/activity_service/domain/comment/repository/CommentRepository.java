package com.dohyeong.activity_service.domain.comment.repository;

import com.dohyeong.activity_service.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findById(long comment_id);
}
