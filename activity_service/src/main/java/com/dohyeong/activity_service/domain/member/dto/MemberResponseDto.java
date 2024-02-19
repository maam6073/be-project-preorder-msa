package com.dohyeong.activity_service.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResponseDto {
    @Getter
    @Builder
    public static class PatchDto{
        private long memberId;
        private String email;
        private String name;
        private String nickname;
        private String profile_img;
        private String description;
    }
}
