package com.example.todo_app.service.impl;

import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.dto.TodoSummaryDTO;
import com.example.todo_app.model.domain.Todo;
import com.example.todo_app.model.domain.User;
import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    TodoRepository todoRepository;
    UserRepository userRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Todo> getAllByUserId(Integer userId) {
        return todoRepository.findAllByUserId(userId);
    }

    @Override
    public void addTodo(TodoDTO todoDTO) {
        String title = todoDTO.getTitle();
        Boolean completed = todoDTO.isCompleted();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(completed);
        todo.setUser(user); // Establece el usuario
        this.todoRepository.save(todo);
    }

    private TodoSummaryDTO convertToTodoSummaryDTO(Todo todo) {
        return new TodoSummaryDTO(todo.getId(), todo.getTitle(), todo.getUser().getUsername(), todo.getUser()
                .getCountry(), todo.isCompleted());
    }

    @Override
    public String updateTodoById(Integer id, TodoDTO updatedTodoDTO, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Todo> optionalTodo = todoRepository.findById(id);
            if (optionalTodo.isPresent()) {
                Todo existingTodo = optionalTodo.get();
                if (existingTodo.getUser().getId().equals(user.getId())) {
                    existingTodo.setTitle(updatedTodoDTO.getTitle());
                    existingTodo.setCompleted(updatedTodoDTO.isCompleted());
                    todoRepository.save(existingTodo);
                    return "Todo updated successfully";
                } else {
                    return "You are not authorized to update this Todo";
                }
            } else {
                return "Todo not found";
            }
        } else {
            return "User not found";
        }
    }

    @Override
    public void delete(Todo todo) {
        this.todoRepository.delete(todo);
    }
    //    public List<TodoSummaryDTO> getTodoSummaries(String title, String username, String sort, String direction) {
//        List<Todo> todos = todoRepository.findByTitleContainingAndUserUsernameContaining(title, username);
//        return todos.stream()
//                .map(todo -> new TodoSummaryDTO(todo.getId(), todo.getTitle(), todo.getUser().getUsername(), todo.getUser().getCountry(), todo.isCompleted()))
//                .sorted((todo1, todo2) -> {
//                    if ("asc".equalsIgnoreCase(direction)) {
//                        return todo1.getTitle().compareToIgnoreCase(todo2.getTitle());
//                    } else {
//                        return todo2.getTitle().compareToIgnoreCase(todo1.getTitle());
//                    }
//                })
//                .collect(Collectors.toList());
//    }

    @Override
    public Page<TodoSummaryDTO> getTodoSummaries(String title, String username, Pageable pageable) {
        Page<Todo> todos;
        if (!title.isEmpty() && !username.isEmpty()) {
            todos = todoRepository.findByTitleContainingAndUser_Username(title, username, pageable);
        } else if (!title.isEmpty()) {
            todos = todoRepository.findByTitleContaining(title, pageable);
        } else if (!username.isEmpty()) {
            todos = todoRepository.findByUser_Username(username, pageable); // Exact match
        } else {
            todos = todoRepository.findAll(pageable);
        }
        return todos.map(this::convertToTodoSummaryDTO);
    }

    @Override
    public void delete(Integer id) {
        todoRepository.deleteById(id);
    }
    @Override
    public void deleteTodoById(int id) {
        // Recuperar el TODO por ID
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        // Obtener el usuario actual
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        // Verificar si el TODO pertenece al usuario actual
        if (!todo.getUser().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized access");
        }

        // Eliminar el TODO
        todoRepository.delete(todo);
    }
}