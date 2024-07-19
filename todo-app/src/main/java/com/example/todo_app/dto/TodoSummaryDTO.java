package com.example.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoSummaryDTO {
    private Integer id;
    private String title;
    private String username;
    private String country;
    private boolean completed;
}
