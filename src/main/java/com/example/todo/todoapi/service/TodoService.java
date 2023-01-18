package com.example.todo.todoapi.service;

import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

   /*
    생성자 주입의 원형
    @RequiredArgsConstructor를 사용하지 않으면 아래와 같이 사용해야 한다

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    */

    // 할 일 목록 조회
    public List<?> retrieve() {
        return todoRepository.findAll();
    }






}
