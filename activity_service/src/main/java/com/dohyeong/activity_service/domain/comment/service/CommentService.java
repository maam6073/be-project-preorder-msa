package com.dohyeong.activity_service.domain.comment.service;

import com.dohyeong.activity_service.domain.activitylog.entity.ActivityType;
import com.dohyeong.activity_service.domain.activitylog.service.ActivityLogService;
import com.dohyeong.activity_service.domain.comment.dto.CommentPostDto;
import com.dohyeong.activity_service.domain.comment.entity.Comment;
import com.dohyeong.activity_service.domain.comment.repository.CommentRepository;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.post.entity.Post;
import com.dohyeong.activity_service.domain.post.repository.PostRepository;
import com.dohyeong.activity_service.global.exception.BusinessLogicException;
import com.dohyeong.activity_service.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
private final CommentRepository commentRepository;
private final PostRepository postRepository;
private final ActivityLogService activityLogService;

    public Comment saveComment(CommentPostDto commentPostDto){

        Post post = postRepository.findById(commentPostDto.getPost_id())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_POST));
        Member curMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = Comment.builder()
                .post(post)
                .member(curMember)
                .comment(commentPostDto.getComment())
                .build();

        activityLogService.logMemberActivity(curMember, post.getMember().getName(),curMember.getName()+" 님이 "
                +post.getMember().getName()+" 님의\""+ post.getTitle() +"\" 글에 댓글을 남겼습니다.", ActivityType.COMMENT);


        return commentRepository.save(comment);
    }
}
