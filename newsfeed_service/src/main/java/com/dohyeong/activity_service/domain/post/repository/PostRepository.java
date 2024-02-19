package com.dohyeong.activity_service.domain.post.repository;

import com.dohyeong.activity_service.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById(long post_id);


}
