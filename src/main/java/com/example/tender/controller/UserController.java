package com.example.tender.controller;

import uz.abror.myproject.payload.Result;
import uz.abror.myproject.payload.UserPayload;
import uz.abror.myproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600L)
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveUser(@RequestBody UserPayload userPayload) {
        Result result = userService.saveUser(userPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editUser(@PathVariable UUID id,
                                           @RequestBody UserPayload userPayload) {
        Result result = userService.editUser(id,userPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Result> getAll() {
        Result result = userService.getAll();
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteUserById(@PathVariable UUID id) {
        Result result = userService.delete(id);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

}
