package com.example.demo.client;
import com.example.demo.dto.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Gọi sang Book Service đang chạy ở cổng 8081
@FeignClient(name = "book-service", url = "http://localhost:8081")
public interface BookClient {
    
    @GetMapping("/books/{id}")
    BookDTO getBookById(@PathVariable("id") Long id);

    @PostMapping("/books/{id}/borrow")
    String borrowBook(@PathVariable("id") Long id);
}
