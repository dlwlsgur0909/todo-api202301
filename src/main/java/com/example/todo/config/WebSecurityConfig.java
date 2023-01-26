package com.example.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration // 설정 파일 등록 어노테이션
@EnableWebSecurity // spring boot가 auto-config 기능을 해주는 어노테이션 (@Configuration은 포함되어 있다)
public class WebSecurityConfig {

    // 패스워드 인코딩 클래스를 등록
    // 외부 라이브러리를 빈으로 등록
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
