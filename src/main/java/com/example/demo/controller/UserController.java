package com.example.demo.controller;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.entity.Users;
import com.example.demo.service.UserService;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody CreateUserDTO createUserDTO) {
        Users newUser = userService.createUser(createUserDTO);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<CreateUserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page, 
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CreateUserDTO> users = userService.getAllUsersWithAuthorities(pageable).getContent();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<CreateUserDTO> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}