package com.example.demo.controllers;

import com.example.demo.DTOs.CreateUserRequest;
import com.example.demo.DTOs.UpdateUserRequest;
import com.example.demo.DTOs.UserResponse;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserByIdWithRatings(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Validated @RequestBody CreateUserRequest newUserRequest) {
        UserResponse createdUser = userService.addUser(newUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Validated @RequestBody UpdateUserRequest updateUserRequest) {
        UserResponse updatedUser = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
