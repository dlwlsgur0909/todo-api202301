package com.example.todo.todoapi.userapi.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRegisteredArgumentsException extends RuntimeException {

    // 아래 두가지를 처리하는 생성자를 만들어야 한다

    // 기본 생성자 (annotation으로 생성)

    // 에러 메세지
    public NoRegisteredArgumentsException(String message) {
        super(message);
    }



}
