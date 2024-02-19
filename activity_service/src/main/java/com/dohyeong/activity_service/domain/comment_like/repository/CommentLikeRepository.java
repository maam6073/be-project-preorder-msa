package com.dohyeong.activity_service.domain.comment_like.repository;


import com.dohyeong.activity_service.domain.comment_like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {

}
