package com.dohyeong.activity_service.global.security.filter;


import com.dohyeong.activity_service.global.security.jwt.JwtTokenProvider;
import com.dohyeong.activity_service.global.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 토큰 받아오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        //토큰이 유효하다면
        if(token != null && jwtTokenProvider.validateToken(token)){
            String key = "JWT_TOKEN:" + jwtTokenProvider.getMemberEmail(token);
            String storedToken = redisUtil.getData(key);

            //로그인 여부 체크
            if(redisUtil.existData(key) && storedToken != null){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        //다음 필터 실행
        chain.doFilter(request, response);
    }
}
