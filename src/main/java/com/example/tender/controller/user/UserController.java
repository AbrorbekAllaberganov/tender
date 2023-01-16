package com.example.tender.controller.user;

import com.example.tender.entity.enums.UserStatus;
import com.example.tender.payload.request.UserLikePayload;
import com.example.tender.payload.request.user.UserInterestFilterPayload;
import com.example.tender.payload.request.user.UserPayload;
import com.example.tender.payload.response.Result;
import com.example.tender.service.UserLikeService;
import com.example.tender.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600L)
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserLikeService userLikeService;


    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editUser(@PathVariable String id,
                                           @RequestBody UserPayload userPayload) {
        log.info("Edit user payload = {}, id = {}", userPayload, id);
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


    @PostMapping("/like")
    public ResponseEntity<Result> likeToUser(@RequestBody UserLikePayload userLikePayload) {
        Result result = userLikeService.saveUserLike(userLikePayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @PutMapping("/like/{userLikeId}")
    public ResponseEntity<Result> likeChangeStatus(@PathVariable("userLikeId") String id) {
        Result result = userLikeService.changeStatus(id);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @GetMapping("/like/{userId}")
    public ResponseEntity<Result> getUserLikes(@PathVariable String userId) {
        Result result = userLikeService.getUserLikes(userId);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }


    @PostMapping("/interests")
    public ResponseEntity<?> findInterests(@RequestBody UserInterestFilterPayload payload,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Find interests payload = {}", payload);
        size = size == 0 ? 10 : size;
        return ResponseEntity.ok(userService.findInterests(payload,size,page));
    }

}
