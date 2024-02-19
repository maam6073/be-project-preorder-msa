package com.dohyeong.activity_service.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MemberPostDto {
    //이메일
    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "이름은 공백 없이 2-10자여야 합니다.")
    @Length(min = 2, max = 10)
    private String name;

    @NotBlank(message = "닉네임은 공백 없이 2-16자여야 합니다.")
    @Length(min = 2, max = 16)
    private String nickname;

    //비밀번호
    @NotBlank(message = "암호는 공백 없이 8-20자여야 합니다.")
    @Length(min = 8, max = 20)
    private String password;

    //자기소개
    @NotBlank
    @Length(max = 150)
    private String description;
}
