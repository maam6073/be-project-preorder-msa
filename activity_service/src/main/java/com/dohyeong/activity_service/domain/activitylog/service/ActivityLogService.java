package com.dohyeong.activity_service.domain.activitylog.service;

import com.dohyeong.activity_service.domain.activitylog.entity.ActivityLog;
import com.dohyeong.activity_service.domain.activitylog.entity.ActivityType;
import com.dohyeong.activity_service.domain.activitylog.repository.ActivityLogRepository;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final MemberService memberService;

    private Member getCurMember(){
        Member findMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberService.getMemberById(findMember.getMember_id());
    }

    // 다른 사용자와 연관이 없는 활동 로그를 기록하는 메서드
    public void logMemberActivity(Member toMember, String activity,ActivityType type){
        ActivityLog saveLog = ActivityLog.builder()
                .followingMember(toMember)
                .type(type)
                .activity(activity).build();
        activityLogRepository.save(saveLog);
    }

//    // 다른 사용자와 연관이 있는 활동 로그를 기록하는 메서드
    public void logMemberActivity(Member toMember,String fromMemberName, String activity,ActivityType type){
        ActivityLog saveLog = ActivityLog.builder()
                .followingMember(toMember)
                .fromMemberName(fromMemberName)
                .type(type)
                .activity(activity).build();
        activityLogRepository.save(saveLog);
    }
    public List<ActivityLog> findMyMemberByActivityTypeOrderByTimestamp(){

        return activityLogRepository.findAllByFromMemberNameOrderByCreatedAtDesc(getCurMember().getName());
    }



    public List<ActivityLog> findFollowingMemberByActivityTypeOrderByTimestamp(){

        return activityLogRepository.findAllByFollowingMemberInOrderByCreatedAtDesc(getCurMember().getFollowings());
    }
}
