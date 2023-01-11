package com.example.tender.controller.user;

import com.example.tender.payload.Result;
import com.example.tender.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @GetMapping("/get-users")
    public ResponseEntity<Result> getAllUsers() {
        Result result = userService.getAll();
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Result> deleteUserById(@PathVariable String id) {
        Result result = userService.delete(id);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }
}
