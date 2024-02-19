package com.dohyeong.activity_service.global.security.controller;

import com.dohyeong.activity_service.domain.member.dto.MemberLoginDto;
import com.dohyeong.activity_service.global.security.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class JwtLoginApiController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> join(@RequestBody @Valid MemberLoginDto.Post memberLoginDto) throws LoginException {

        return ResponseEntity.ok(loginService.login(memberLoginDto));
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest){

        loginService.logout();
        return ResponseEntity.ok().build();
    }
}
