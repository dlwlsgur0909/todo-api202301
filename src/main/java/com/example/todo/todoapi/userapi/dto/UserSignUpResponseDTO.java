package com.example.todo.todoapi.userapi.dto;

import com.example.todo.todoapi.userapi.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

// 회원가입 완료 후 클라이언트에게 응답할 데이터를 담는 객체
@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
@Builder
public class UserSignUpResponseDTO {

    private String email;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    // entity를 DTO로 변경하는 생성자

    public UserSignUpResponseDTO(UserEntity entity) {
        this.email = entity.getEmail();
        this.userName = entity.getUserName();
        this.joinDate = entity.getJoinDate();

    }


}
