package com.dohyeong.activity_service.domain.newsfeed.service;

import com.dohyeong.activity_service.domain.activitylog.entity.ActivityLog;
import com.dohyeong.activity_service.domain.activitylog.service.ActivityLogService;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.newsfeed.dto.MultiResponse;
import com.dohyeong.activity_service.domain.newsfeed.dto.NewsFeedResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedService {


    private final ActivityLogService activityLogService;

    //팔로잉한 사용자에 대한 해당 로그내용 가져오기
    public MultiResponse getNewsFeedForMember(){
        List<ActivityLog> followingActivityLogs = activityLogService.findFollowingMemberByActivityTypeOrderByTimestamp();
        List<Object> responses = new ArrayList<>();

        Member curMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(ActivityLog log : followingActivityLogs){
            //팔로잉한 사용자 중에서 현재로그인멤버의 컨텐츠와 연관이 있다면
            if(log.getFromMemberName() != null && log.getFromMemberName().equals(curMember.getName())){
                NewsFeedResponseDto.MyActivityResponseDto myDto = new NewsFeedResponseDto.MyActivityResponseDto();
                StringBuilder activity = new StringBuilder();
                switch (log.getType()){
                    case POST_LIKE:
                        myDto.setType(log.getType());
                        myDto.setActivity(log.getActivity());
                        break;
                    case COMMENT:
                        myDto.setType(log.getType());
                        activity.append(log.getFollowingMember().getName()+ "님이 " + log.getActivity().substring(log.getActivity().indexOf("\""),log.getActivity().lastIndexOf("\""))
                        +" 포스트에 댓글을 남겼습니다.");
                        myDto.setActivity(activity.toString());
                        break;
                    case COMMENT_LIKE:
                        myDto.setType(log.getType());
                        activity.append(log.getFollowingMember().getName()+ "님이" + " 나의 댓글을 좋아합니다.");
                        myDto.setActivity(activity.toString());
                        break;
                    case  FOLLOW:
                        myDto.setType(log.getType());
                        activity.append(log.getFollowingMember()+"님이 나를 팔로우합니다.");
                        myDto.setActivity(activity.toString());
                        break;
                }
                responses.add(myDto);
            }
            else {
                NewsFeedResponseDto newsFeedResponseDto = NewsFeedResponseDto.builder()
                        .activityId(log.getActivity_id())
                        .time(log.getCreatedAt())
                        .memberName(log.getFollowingMember().getName())
                        .memberProfileUrl(log.getFollowingMember().getProfile_img())
                        .type(log.getType())
                        .activity(log.getActivity())
                        .build();
                responses.add(newsFeedResponseDto);
            }
        }

        return new MultiResponse(responses);
    }


    //나의 활동에 연관이있는 해당 로그내용 가져오기
    public MultiResponse getMyNewsFeedForMember(){
        List<ActivityLog> myActivityLogs = activityLogService.findMyMemberByActivityTypeOrderByTimestamp();
        List<Object> responses = new ArrayList<>();
        for(ActivityLog log : myActivityLogs) {
            //팔로잉한 사용자 중에서 현재로그인멤버의 컨텐츠와 연관이 있다면
            NewsFeedResponseDto.MyActivityResponseDto myDto = new NewsFeedResponseDto.MyActivityResponseDto();
            StringBuilder activity = new StringBuilder();
            switch (log.getType()) {
                case POST_LIKE:
                    myDto.setType(log.getType());
                    myDto.setActivity(log.getActivity());
                    break;
                case COMMENT:
                    myDto.setType(log.getType());
                    activity.append(log.getFollowingMember().getName() + "님이 " + log.getActivity().substring(log.getActivity().indexOf("\"")+1, log.getActivity().lastIndexOf("\""))
                            + " 포스트에 댓글을 남겼습니다.");
                    myDto.setActivity(activity.toString());
                    break;
                case COMMENT_LIKE:
                    myDto.setType(log.getType());
                    activity.append(log.getFollowingMember().getName() + "님이" + " 나의 댓글을 좋아합니다.");
                    myDto.setActivity(activity.toString());
                    break;
                case FOLLOW:
                    myDto.setType(log.getType());
                    activity.append(log.getFollowingMember() + "님이 나를 팔로우합니다.");
                    myDto.setActivity(activity.toString());
                    break;
            }
            responses.add(myDto);
        }
        return new MultiResponse(responses);
    }

}
