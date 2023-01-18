package com.example.todo.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoListResponseDTO {

    private String error;
    private List<TodoDetailResponseDTO> todos;



}
