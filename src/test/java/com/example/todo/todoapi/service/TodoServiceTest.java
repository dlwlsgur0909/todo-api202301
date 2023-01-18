package com.example.todo.todoapi.service;

import com.example.todo.dto.request.TodoCreateRequestDTO;
import com.example.todo.dto.request.TodoModifyRequestDTO;
import com.example.todo.dto.response.TodoDetailResponseDTO;
import com.example.todo.dto.response.TodoListResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Commit
class TodoServiceTest {


    @Autowired
    TodoService todoService;

    @BeforeEach
    void beforeInsert() {
        TodoCreateRequestDTO dto1 = TodoCreateRequestDTO.builder()
                .title("저녁 장보기")
                .build();

        TodoCreateRequestDTO dto2 = TodoCreateRequestDTO.builder()
                .title("식물 물주기")
                .build();

        TodoCreateRequestDTO dto3 = TodoCreateRequestDTO.builder()
                .title("음악 감상하기")
                .build();

        todoService.create(dto1);
        todoService.create(dto2);
        todoService.create(dto3);
    }

    @Test
    @DisplayName("새로운 할 일을 등록하면 생성되는 리스트는 할 일이 4개 들어있어야 한다")
    void createTest() {

        // given
        TodoCreateRequestDTO newTodo = TodoCreateRequestDTO.builder().title("새로운 할 일~~").build();

        // when
        TodoListResponseDTO responseDTO = todoService.create(newTodo);

        // then
        List<TodoDetailResponseDTO> todos = responseDTO.getTodos();
        Assertions.assertEquals(4, todos.size());

        System.out.println("========================");
        todos.forEach(System.out::println);
    }

    @Test
    @DisplayName("두번째 할 일의 제목을 수정수정으로 수정하고 할일 완료 처리를 해야한다.")
    void updateTest() {

        // given
        String newTitle = "수정수정";
        boolean newDone = true;

        TodoModifyRequestDTO modifyRequestDTO = TodoModifyRequestDTO.builder()
                                                    .title(newTitle)
                                                    .done(newDone)
                                                    .build();

        // when
        TodoDetailResponseDTO targetTodo = todoService.retrieve().getTodos().get(1);
        TodoListResponseDTO responseDTO = todoService.update(targetTodo.getId(), modifyRequestDTO);

        // then
        Assertions.assertEquals("수정수정", responseDTO.getTodos().get(1).getTitle());
        Assertions.assertTrue(responseDTO.getTodos().get(1).isDone());

    }


    @Test
    @DisplayName("3번째 할 일을 삭제했을때 생성되는 할 일 목록에는 할 일이 2개 들어있어야 한다")
    void deleteTest() {

        // given
        String targetId = todoService.retrieve().getTodos().get(2).getId();

        // when
        TodoListResponseDTO responseDTO = todoService.delete(targetId);
        List<TodoDetailResponseDTO> todoList = responseDTO.getTodos();

        // then
        Assertions.assertEquals(2, todoList.size());
        todoList.forEach(System.out::println);



    }






}