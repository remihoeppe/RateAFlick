package com.example.demo.DTOs;

import jakarta.validation.constraints.*;

public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message =
            "Name must be between 3 and 100 char long")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    public CreateUserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
