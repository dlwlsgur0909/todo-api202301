package com.example.todo.config;

import com.example.todo.security.JwtAuthFilter;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

// @Configuration // 설정 파일 등록 어노테이션
@EnableWebSecurity // spring boot가 auto-config 기능을 해주는 어노테이션 (@Configuration은 포함되어 있다)
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;

    // 패스워드 인코딩 클래스를 등록
    // 외부 라이브러리를 빈으로 등록
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 시큐리티 빌더
        http.cors() // 크로스 오리진 정책
                .and()
                .csrf() // csrf 정책
                .disable() // 사용 안함
                .httpBasic().disable() // 기본 시큐리티 인증 해제, 토큰 인증 사용을 위해서
                // 세션 기반 인증 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 인증 요청 중 '/'와 '/api/auth'로 시작되는 경로는 인증하지 않고 모두 허용
                .authorizeRequests().antMatchers("/", "/api/auth/**").permitAll()
                // 그 외에 모든 경로는 인증을 거쳐야함
                .anyRequest().authenticated();


        // 토큰 인증 필터 등록
//        http.addFilterAfter(커스텀필터, 누구 뒤에 붙일지)
        http.addFilterAfter(
                jwtAuthFilter,
                CorsFilter.class // import 주의 : Spring으로
        );

        return http.build();

    }

}
