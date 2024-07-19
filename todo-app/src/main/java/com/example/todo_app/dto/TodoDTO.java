package com.example.todo_app.dto;

import com.example.todo_app.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private String title;
    private boolean completed;
}
