package com.dohyeong.activity_service.domain.member.dto;


import lombok.Getter;
import lombok.Setter;


public class MemberLoginDto{

    @Getter
    public static class Post {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class Response {
        private long email;
        private String about;
    }
}
