package com.dohyeong.activity_service.domain.post.controller;

import com.dohyeong.activity_service.domain.post.dto.CreatePostDto;
import com.dohyeong.activity_service.domain.post.dto.PostResponseDto;
import com.dohyeong.activity_service.domain.post.entity.Post;
import com.dohyeong.activity_service.domain.post.mapper.PostMapper;
import com.dohyeong.activity_service.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@Validated
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    //글작성
    @PostMapping()
    public ResponseEntity<PostResponseDto.createResponseDto> createPost(@Valid @RequestBody CreatePostDto createPostDto){
        Post post = postService.savePost(postMapper.createPostDtoToPost(createPostDto));
        PostResponseDto.createResponseDto response = postMapper.postToPostResponseDto(post);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}

