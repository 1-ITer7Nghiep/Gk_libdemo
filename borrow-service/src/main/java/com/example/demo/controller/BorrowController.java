package com.example.demo.controller;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // API Mượn sách
    // POST http://localhost:8083/borrows
    // Body: { "userId": 1, "bookId": 1 }
    @PostMapping
    public ResponseEntity<?> createBorrow(@RequestBody Map<String, Long> payload) {
        try {
            Long userId = payload.get("userId");
            Long bookId = payload.get("bookId");
            
            BorrowRecord record = borrowService.borrowBook(userId, bookId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API Xem lịch sử mượn của 1 user
    // GET http://localhost:8083/borrows/user/1
    @GetMapping("/user/{userId}")
    public List<BorrowRecord> getHistory(@PathVariable Long userId) {
        return borrowService.getBorrowHistory(userId);
    }
}
