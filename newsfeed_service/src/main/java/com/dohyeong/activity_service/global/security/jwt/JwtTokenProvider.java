package com.dohyeong.activity_service.global.security.jwt;


import com.dohyeong.activity_service.domain.member.service.MemberService;
import com.dohyeong.activity_service.global.security.dto.UserLoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;


@RequiredArgsConstructor
@Component @Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    private final long tokenValidTime = 30 * 60 * 1000L;    //토큰 유효시간 -> 30분

    private final MemberService memberService;

    //객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init(){
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    //토큰 생성
    public String createToken(String memberEmail){
        Claims claims = Jwts.claims().setSubject(memberEmail);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    //인증 정보 조회
    public Authentication getAuthentication(String token){
        //Spring Security에서 제공하는 메서드 override해서 사용해야 함.
        UserLoginDto userLoginDto = memberService.loadUserByUsername(this.getMemberEmail(token));
        return new UsernamePasswordAuthenticationToken(userLoginDto.getMember(), "", userLoginDto.getAuthorities());
    }

    //토큰에서 member 정보 추출
    public String getMemberEmail(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    //토큰 유효성 만료일자 확인
    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            log.debug(e.getMessage());
            return false;
        }
    }

    //Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }


    //유효시간 반환
    public long getExpiration(){
        return tokenValidTime;
    }
}
