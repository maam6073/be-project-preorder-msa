package com.dohyeong.activity_service.domain.post_like.repository;

import com.dohyeong.activity_service.domain.post_like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

}
