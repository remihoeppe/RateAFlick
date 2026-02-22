package com.example.demo.DTOs;

/**
 * Response for GET /api/v1/users (list). Only id, name, email — no ratings field.
 */
public class UserListResponse {
    private Long id;
    private String name;
    private String email;

    public UserListResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
