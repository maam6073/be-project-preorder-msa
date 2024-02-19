package com.dohyeong.activity_service.domain.post.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreatePostDto {
    @Length(max = 20)
    private String title;


    @Length(max = 150)
    private String body;
}
