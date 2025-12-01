package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // API: Lấy danh sách tất cả user
    // GET http://localhost:8082/users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // API: Lấy thông tin 1 user
    // GET http://localhost:8082/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    // API: Tạo user mới
    // POST http://localhost:8082/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}