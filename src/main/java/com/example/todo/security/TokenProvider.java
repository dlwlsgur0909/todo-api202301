package com.example.todo.security;

// 토큰을 발급하고, 서명 위조를 확인해주는 객체

import com.example.todo.todoapi.userapi.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class TokenProvider {

    // 토큰 서명에 사용할 불변성을 가진 비밀 키 (512바이트 이상의 랜덤 문자열)
    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";

    // 토큰 발급 메서드
    public String createToken(UserEntity userEntity) {
        // 만료시간 설정
        Date expireDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        // 토큰 생성
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512) // header에 들어갈 서명
                .setSubject(userEntity.getId()) // sub: 토큰 식별자
                .setIssuer("todo app") // iss: 발급자 정보
                .setIssuedAt(new Date()) // iat: 토큰 발급 시간
                .setExpiration(expireDate) // exp: 토큰 만료 시간
                .compact();
    }


    /**
     * 클라이언트가 보낸 토큰을 디코딩 및 파싱해서 토큰 위조 여부 확인
     * @param token - 클라이언트가 전송한 인코딩된 토큰
     * @return - 토큰에서 subject(userId)를 꺼내서 반환
     */
    public String validateAndGetUserId(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // token 발급 당시 서명을 넣어줌
                .build()
                // 토큰을 디코딩해서 서명기록을 파싱하고 클라이언트 토큰의 서명과 서버 발급 당시 서명을 비교
                // 위조되지 않았다면 body에 payload(claims)를 리턴
                // 위조되었다면 예외를 발생 시킴
                .parseClaimsJwt(token)
                .getBody();


        return claims.getSubject();
    }



}
