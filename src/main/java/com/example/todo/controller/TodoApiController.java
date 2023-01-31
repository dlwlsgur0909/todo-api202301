package com.example.todo.controller;

import com.example.todo.dto.request.TodoCreateRequestDTO;
import com.example.todo.dto.request.TodoModifyRequestDTO;
import com.example.todo.dto.response.TodoDetailResponseDTO;
import com.example.todo.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
//@CrossOrigin  // CORS 허용 설정 (모두 허용)
public class TodoApiController {

    private final TodoService todoService;

    // 할 일 등록 요청
    @PostMapping
    public ResponseEntity<?> createTodo(
            @Validated @RequestBody TodoCreateRequestDTO requestDTO, @AuthenticationPrincipal  String userId,
            BindingResult result) {

        if(result.hasErrors()) {
            log.warn("Validation Fail - {}", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try {
            return ResponseEntity
                    .ok()
                    .body(todoService.create(requestDTO, userId));
        }catch(RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()));
        }
    }

    // 할 일 삭제 요청
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String todoId, @AuthenticationPrincipal  String userId) {
        log.info("/api/todos/{} DELETE request!", todoId);

        if(todoId == null || todoId.equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(TodoListResponseDTO.builder().error("ID를 전달해주세요"));
        }

        try {
            TodoListResponseDTO responseDTO = todoService.delete(todoId, userId);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(TodoListResponseDTO.builder().error(e.getMessage()));
        }

    }

    // 할 일 목록요청
    @GetMapping
    public ResponseEntity<?> getTodoList(@AuthenticationPrincipal  String userId) { // 인증 완료 처리 시 등록했던 값을 넣어줌

        try {
            TodoListResponseDTO todoList = todoService.retrieve(userId);

//            if(todoList.getTodos().size()==0) {
//                return ResponseEntity
//                        .ok()
//                        .body("할 일이 없습니다!");
//            }

            return ResponseEntity
                    .ok()
                    .body(todoList);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Internal Server Error Occurred - " + e.getMessage());
        }
    }

    // 할 일 수정요청
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTodo(
            @PathVariable("id") String todoId,
            @Validated @RequestBody TodoModifyRequestDTO requestDTO,
            @AuthenticationPrincipal  String userId,
            BindingResult result) {

        if(result.hasErrors()) {
            log.warn("Validation Fail for PATCH request - {}", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try {
            TodoListResponseDTO responseDTO = todoService.update(todoId, requestDTO, userId);
            List<TodoDetailResponseDTO> todoList = responseDTO.getTodos();
            return ResponseEntity
                    .ok()
                    .body(todoList);
        } catch (Exception e) {
            log.error("PATCH request failed - {}", e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO.builder().error(e.getMessage()));
        }

    }





}
