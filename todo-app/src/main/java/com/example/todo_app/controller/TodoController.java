package com.example.todo_app.controller;

import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.dto.TodoSummaryDTO;
import com.example.todo_app.model.domain.Todo;
import com.example.todo_app.service.TodoService;
import com.example.todo_app.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todoapp")
public class TodoController {
    private final UserService userService;
    private final TodoService todoService;

    public TodoController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @PostMapping("/user/addTodo")
    public ResponseEntity<String> addTodo(@RequestBody TodoDTO todoDTO) {
        todoService.addTodo(todoDTO);
        return new ResponseEntity<>("Todo has been added", HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<TodoSummaryDTO>> getAllTodos(
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
        Page<TodoSummaryDTO> summaries = todoService.getTodoSummaries(title, username, pageable);
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/todos")
    public ResponseEntity<List<Todo>> getAllUserTodos(@PathVariable(name = "id") Integer userId, Model model) {
        List<Todo> todoList = todoService.getAllByUserId(userId);
        model.addAttribute("todoList", todoList);
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable("id") Integer id, @RequestBody TodoDTO updatedTodoDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        String response = todoService.updateTodoById(id, updatedTodoDTO, username);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTodo(
            @PathVariable("id") int id) {
        try {
            todoService.deleteTodoById(id);
            return new ResponseEntity<>("Todo deleted successfully", HttpStatus.NO_CONTENT);
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}