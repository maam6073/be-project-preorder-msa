package com.dohyeong.activity_service.domain.comment_like.controller;

import com.dohyeong.activity_service.domain.comment_like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment-like")
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;
    @PostMapping("/{comment-id}")
    public ResponseEntity commentLike(@PathVariable("comment-id") long commentId){
        commentLikeService.saveCommentLike(commentId);
        return ResponseEntity.ok().build();
    }
}
