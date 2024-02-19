package com.dohyeong.activity_service.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

public class PostResponseDto {
    @Getter
    @Builder
    public static class createResponseDto{
        private Long post_id;
        private Long member_id;
        private String title;
        private String body;
    }
}
