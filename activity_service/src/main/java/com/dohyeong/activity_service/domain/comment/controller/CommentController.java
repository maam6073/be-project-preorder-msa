package com.dohyeong.activity_service.domain.comment.controller;

import com.dohyeong.activity_service.domain.comment.dto.CommentPostDto;
import com.dohyeong.activity_service.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Validated
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentPostDto commentPostDto){
        commentService.saveComment(commentPostDto);
        return ResponseEntity.ok().build();
    }
}
