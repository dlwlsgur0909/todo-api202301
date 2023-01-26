package com.example.todo.todoapi.userapi.dto;

// 회원가입 시 클라이언트가 보낸 데이터를 담는 객체

import com.example.todo.todoapi.userapi.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
@Builder
public class UserSignUpDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Size(min = 2, max = 5)
    private String userName;


    // entity로 변환 메서드
    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(this.email)
                .password(this.password)
                .userName(this.userName)
                .build();
    }


}
