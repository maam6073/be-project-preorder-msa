package com.dohyeong.activity_service.domain.follow.repository;

import com.dohyeong.activity_service.domain.follow.entity.Follow;
import com.dohyeong.activity_service.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    //팔로워 팔로잉 사용자에대한 중복체크
    //엔티티에서 컬럼속성중 유니크체크를하여 간편하게 개선할수도있다.
    @Query("select f from Follow f where f.follower = :follower and f.following = :following")
    Optional<Follow> findFollow(@Param("follower") Member follower, @Param("following") Member following);


}
