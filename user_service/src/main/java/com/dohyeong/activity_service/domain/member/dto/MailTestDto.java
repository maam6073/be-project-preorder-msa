package com.dohyeong.activity_service.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MailTestDto {
    @Email @NotBlank
    private String email;

    private String code;
}
