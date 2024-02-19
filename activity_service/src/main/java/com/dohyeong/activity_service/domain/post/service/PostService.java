package com.dohyeong.activity_service.domain.post.service;


import com.dohyeong.activity_service.domain.activitylog.entity.ActivityType;
import com.dohyeong.activity_service.domain.activitylog.service.ActivityLogService;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.post.entity.Post;
import com.dohyeong.activity_service.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ActivityLogService activityLogService;

    //글 저장
    public Post savePost(Post post){
        Member curMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post savePost =
                Post.builder()
                        .member(curMember)
                        .title(post.getTitle())
                        .body(post.getBody())
                        .build();
        activityLogService.logMemberActivity(curMember, curMember.getName()+" 님이 글을 남겼습니다.\n"+post.getTitle()+"\n"
                                                                +post.getBody(), ActivityType.POST);
        return postRepository.save(savePost);
    }

    //글 수정


    //글 삭제


    //글 확인
}
