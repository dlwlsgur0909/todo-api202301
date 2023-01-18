package com.example.todo.dto.request;

import com.example.todo.todoapi.entity.TodoEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class TodoCreateRequestDTO {

    @NotBlank
    @Size(min = 2, max = 10)
    private String title;

    // DTO를 entity로
    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .title(this.title)
                .build();
    }

}
