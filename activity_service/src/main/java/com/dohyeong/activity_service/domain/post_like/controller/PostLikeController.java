package com.dohyeong.activity_service.domain.post_like.controller;


import com.dohyeong.activity_service.domain.post_like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-like")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;
    @PostMapping("/{post-id}")
    public ResponseEntity postLike(@PathVariable("post-id") long postId){
        postLikeService.savePostLike(postId);
        return ResponseEntity.ok().build();
    }
}
