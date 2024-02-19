package com.dohyeong.activity_service.domain.activitylog.entity;

import lombok.Getter;

@Getter
public enum ActivityType {
    POST("글"),
    POST_LIKE("글 좋아요"),
    COMMENT("댓글"),
    COMMENT_LIKE("댓글 좋아요"),
    FOLLOW("팔로우");

    private final String description;

    ActivityType(String description) {this.description = description;}
}
