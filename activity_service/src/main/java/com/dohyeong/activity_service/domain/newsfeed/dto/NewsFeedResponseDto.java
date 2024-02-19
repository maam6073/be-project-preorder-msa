package com.dohyeong.activity_service.domain.newsfeed.dto;

import com.dohyeong.activity_service.domain.activitylog.entity.ActivityType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
public class NewsFeedResponseDto {
    private Long activityId;
    private LocalDateTime time;
    private String memberName;
    private String memberProfileUrl;
    private ActivityType type;
    private String activity;

    @Builder
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyActivityResponseDto{
        private ActivityType type;
        private String activity;
    }
}
