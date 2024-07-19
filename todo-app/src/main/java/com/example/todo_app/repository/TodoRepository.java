package com.example.todo_app.repository;

import com.example.todo_app.model.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findAllByUserId(Integer userId);

    Page<Todo> findByTitleContaining(String title, Pageable pageable);

    Page<Todo> findByUser_Username(String username, Pageable pageable); // Exact match

    Page<Todo> findByTitleContainingAndUser_Username(String title, String username, Pageable pageable);
}
