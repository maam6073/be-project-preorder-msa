package com.dohyeong.activity_service.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MemberPatchDto {
    //이름
    @NotBlank
    @Length(max = 10)
    private String nickname;

    //자기소개
    @NotBlank
    @Length(max = 150)
    private String description;
}
