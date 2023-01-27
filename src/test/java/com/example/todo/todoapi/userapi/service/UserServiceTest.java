package com.example.todo.todoapi.userapi.service;

import com.example.todo.todoapi.userapi.dto.UserSignUpDTO;
import com.example.todo.todoapi.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.todoapi.userapi.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    @DisplayName("중복된 이메일이 포함된 회원정보로 가입하면 RuntimeException이 발생해야 한다")
    void validateEmailTest() {

        // given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("abc1234@def.com")
                .password("1234")
                .userName("키키킥")
                .build();

        // when / then
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.create(dto);
        });
    }

    @Test
    @DisplayName("검증된 회원정보로 가입하면 회원가입에 성공해야 한다")
    void createTest() {

        // given
        UserSignUpDTO dto = UserSignUpDTO.builder()
                .email("qwer23@def.com")
                .password("1234")
                .userName("암호맨2")
                .build();

        // when
        UserSignUpResponseDTO responseDTO = userService.create(dto);

        // then
        Assertions.assertEquals("암호맨2", responseDTO.getUserName());
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 시도 시 Exception이 발생해야 한다")
    void noUserTest() {

        // given
        String email = "notexist@aaa.com";
        String password = "1234";

        // then
        Assertions.assertThrows(RuntimeException.class, () -> {
            // when
            userService.getByCredentials(email, password);
        });
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인 시도 시 Exception이 발생해야 한다")
    void wrongPasswordTest() {

        // given
        String email = "postman@naver.com";
        String password = "wrong password";

        // then
        Assertions.assertThrows(RuntimeException.class, () -> {
            // when
            userService.getByCredentials(email, password);
        });
    }

    @Test
    @DisplayName("정확한 정보로 로그인 시도 시 회원정보가 반환되어야 한다")
    void loginTest() {

        // given
        String email = "aa@aa.com";
        String password = "qwer12#$";

        // when
        UserEntity loginUser = userService.getByCredentials(email, password);

        // then
        Assertions.assertEquals("야호", loginUser.getUserName());
    }


}