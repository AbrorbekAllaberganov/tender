package com.example.tender.controller.user;

import com.example.tender.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tender.payload.Result;
import com.example.tender.payload.UserPayload;
import com.example.tender.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600L)
public class UserController {
    private final UserService userService;


    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editUser(@PathVariable String id,
                                           @RequestBody UserPayload userPayload) {
        Result result = userService.editUser(id, userPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @PutMapping("/change-location")
    public ResponseEntity<Result> locationChange(@RequestParam("userId") String userId,
                                                 @RequestParam("lon") long lon,
                                                 @RequestParam("lat") long lat) {
        Result result = userService.changeLocation(userId, lon, lat);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @PutMapping("/{status}")
    public ResponseEntity<Result> changeStatus(@PathVariable("status") UserStatus status) {
        Result result = userService.changeStatus(status);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }
}
