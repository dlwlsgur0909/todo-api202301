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

    // 로그인 검증
    public UserEntity getByCredentials(final String email, final String rawPassword) {

        // 입력한 이메일을 통해 회원정보 조회
        UserEntity originalUser = userRepository.findByEmail(email);

        if(originalUser == null) {
            throw new RuntimeException("가입된 회원이 아닙니다");
        }

        // 비밀번호 검증
        if(!passwordEncoder.matches(rawPassword, originalUser.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다");
        }

        log.info("{}님 로그인 성공!", originalUser.getUserName());

        return originalUser;
    }





}
