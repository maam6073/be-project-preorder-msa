package com.dohyeong.activity_service.global.security.service;

import com.dohyeong.activity_service.domain.member.dto.MemberLoginDto;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.member.repository.MemberRepository;
import com.dohyeong.activity_service.global.security.jwt.JwtTokenProvider;
import com.dohyeong.activity_service.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    //로그인
    public String login(MemberLoginDto.Post request) throws LoginException {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new LoginException("LoginErrorCode.ID_NOT_FOUND"));

        //PW 체크
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new LoginException("PASSWORD_INVALID");
        }

        String token = jwtTokenProvider.createToken(member.getEmail());
        redisUtil.setDataExpire("JWT_TOKEN:" + request.getEmail(), token, jwtTokenProvider.getExpiration());
        return token;
    }

    //로그아웃
    public void logout(){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(redisUtil.getData("JWT_TOKEN:" + member.getEmail()) != null)
            redisUtil.deleteData("JWT_TOKEN:" + member.getEmail());
    }
}
