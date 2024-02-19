package com.dohyeong.activity_service.domain.follow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FollowPostDto {
    @NotBlank
    private String name;
}
