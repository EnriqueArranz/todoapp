package com.example.todo_app.service;

import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.dto.TodoSummaryDTO;
import com.example.todo_app.model.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoService {
    void delete(Todo todo);


    Page<TodoSummaryDTO> getTodoSummaries(String title, String username, Pageable pageable);

    void addTodo(TodoDTO todoDTO);

    String updateTodoById(Integer id, TodoDTO updatedTodoDTO, String username);

    void delete(Integer id);

    List<Todo> getAllByUserId(Integer userId);

    void deleteTodoById(int id);
}
