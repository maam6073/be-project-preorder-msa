package com.dohyeong.activity_service.global.config;


import com.dohyeong.activity_service.global.security.filter.JwtTokenFilter;
import com.dohyeong.activity_service.global.security.jwt.JwtTokenProvider;
import com.dohyeong.activity_service.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic((httpBasic) ->
                        httpBasic.disable())
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 사용안함
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider,redisUtil), UsernamePasswordAuthenticationFilter.class)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                                )
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                                .requestMatchers("/members/emailSend").permitAll()
                                .requestMatchers("/members/emailConfirm").permitAll()
                                .requestMatchers("/members").permitAll()
                                .requestMatchers("/auth/login").permitAll()
                                .requestMatchers("/auth/logout").authenticated()
                                .requestMatchers("/members/password").authenticated()
                                .requestMatchers("/follow/**").authenticated()
                                .requestMatchers("/post/**").authenticated()
                                .requestMatchers("/comment/**").authenticated()
                                .requestMatchers("/post-like/**").authenticated()
                                .requestMatchers("/comment-like/**").authenticated()
                                .requestMatchers("/newsfeed/**").authenticated()
                )
                .formLogin((formLogin) -> formLogin.disable());

        return http.build();
    }


}
