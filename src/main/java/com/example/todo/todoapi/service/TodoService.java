package com.example.todo.todoapi.service;

import com.example.todo.dto.request.TodoCreateRequestDTO;
import com.example.todo.dto.request.TodoModifyRequestDTO;
import com.example.todo.dto.response.TodoDetailResponseDTO;
import com.example.todo.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public TodoListResponseDTO retrieve() {
        List<TodoEntity> entityList = todoRepository.findAll();

        List<TodoDetailResponseDTO> dtoList = entityList.stream()
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());

        return TodoListResponseDTO.builder()
                .todos(dtoList)
                .build();
    }

    // 할 일 등록
    public TodoListResponseDTO create(final TodoCreateRequestDTO createRequestDTO)
        throws RuntimeException
    {

        todoRepository.save(createRequestDTO.toEntity());
        log.info("할 일이 저장되었습니다. 제목: {}", createRequestDTO.getTitle());

        // 갱신된 목록 반환
        return retrieve();
    }

    // 할 일 수정(제목, 완료 여부)
    public TodoListResponseDTO update(@PathVariable("id") final String id, final TodoModifyRequestDTO modifyRequestDTO) {

        Optional<TodoEntity> targetEntity = todoRepository.findById(id);

        targetEntity.ifPresent(entity -> {
            if(modifyRequestDTO.getTitle()!=null) {
                entity.setTitle(modifyRequestDTO.getTitle());
            }
            System.out.println(modifyRequestDTO.isDone());
            if(modifyRequestDTO.isDone()) {
                entity.setDone(true);
            }
            todoRepository.save(entity);
        });

        return retrieve();
    }

    // 할 일 삭제
    public TodoListResponseDTO delete(final String id) {

        try {
            todoRepository.deleteById(id);
        }catch (Exception e) {
            log.error("id가 존재하지 않아 삭제에 실패했습니다. - ID: {}, err: {}", id, e.getMessage());
            throw new RuntimeException("id가 존재하지 않아 삭제에 실패했습니다.");
        }

        return retrieve();
    }








}
