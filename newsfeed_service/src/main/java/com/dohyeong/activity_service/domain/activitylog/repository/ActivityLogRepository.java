package com.dohyeong.activity_service.domain.activitylog.repository;

import com.dohyeong.activity_service.domain.activitylog.entity.ActivityLog;
import com.dohyeong.activity_service.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog,Long> {
    List<ActivityLog> findAllByFollowingMemberInOrderByCreatedAtDesc(List<Follow> followingMembers);
    List<ActivityLog> findAllByFromMemberNameOrderByCreatedAtDesc(String fromMemberName);
}
