package com.example.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String username;
    private String street;
    private String city;
    private String zipcode;
    private String country;

}
