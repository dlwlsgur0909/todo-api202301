package com.example.todo.todoapi.userapi.service;

import com.example.todo.todoapi.userapi.Repository.UserRepository;
import com.example.todo.todoapi.userapi.dto.UserSignUpDTO;
import com.example.todo.todoapi.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.todoapi.userapi.entity.UserEntity;
import com.example.todo.todoapi.userapi.exception.DuplicatedEmailException;
import com.example.todo.todoapi.userapi.exception.NoRegisteredArgumentsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 처리
    public UserSignUpResponseDTO create(final UserSignUpDTO userSignUpDTO) {
        if(userSignUpDTO == null) {
            throw new NoRegisteredArgumentsException("가입 정보가 없습니다");
        }

        final String email = userSignUpDTO.getEmail();
        if(userRepository.existsByEmail(email)) {
            log.warn("Email already exist - {}", email);
            throw new DuplicatedEmailException("duplicate email");
        }

        // 패스워드 인코딩
        String rawPassword = userSignUpDTO.getPassword(); // 평문 암호
        userSignUpDTO.setPassword(passwordEncoder.encode(rawPassword)); // 암호화 처리

        UserEntity savedUser = userRepository.save(userSignUpDTO.toEntity());

        log.info("회원 가입 성공!! - user_id : {}", savedUser.getId());

        return new UserSignUpResponseDTO(savedUser);

    }

    // 이메일 중복 확인
    public boolean isDuplicate(String email) {
        if(email == null) {
            throw new RuntimeException("이메일 값이 없습니다.");
        }
        return userRepository.existsByEmail(email);
    }
}
