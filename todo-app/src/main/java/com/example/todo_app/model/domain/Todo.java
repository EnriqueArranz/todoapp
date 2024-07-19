package com.example.todo_app.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="`Id`")
    private Integer id;
    @Column(name="`Title`")
    private String title;
    @Column(name="`Completed`")
    private Boolean completed;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Todo(String title, Boolean completed, User user) {
        this.title = title;
        this.completed = completed;
        this.user = user;
    }

    public boolean isCompleted() {
        return completed;
    }
}