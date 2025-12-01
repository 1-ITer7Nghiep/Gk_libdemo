package com.example.demo.client;
import com.example.demo.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Gọi sang User Service đang chạy ở cổng 8082
@FeignClient(name = "user-service", url = "http://localhost:8082")
public interface UserClient {
    
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}

