package com.example.tender.controller.user;

import com.example.tender.entity.enums.Language;
import com.example.tender.entity.enums.UserStatus;
import com.example.tender.entity.enums.UserType;
import com.example.tender.payload.request.UserLikePayload;
import com.example.tender.payload.request.user.UserInterestFilterPayload;
import com.example.tender.payload.request.user.UserPayload;
import com.example.tender.payload.request.user.UserPostReqDTO;
import com.example.tender.payload.response.Result;
import com.example.tender.service.UserLikeService;
import com.example.tender.service.UserPostService;
import com.example.tender.service.UserService;
import com.example.tender.service.UserStoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600L)
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserLikeService userLikeService;
    private final UserStoryService userStoryService;
    private final UserPostService userPostService;

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

    @PutMapping("/like")
    public ResponseEntity<Result> likeChangeStatus(@RequestParam("userLikeId") String id,
                                                   @RequestParam("type") String likeType) {
        Result result = userLikeService.changeStatus(id, likeType);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @GetMapping("/like/{userId}")
    public ResponseEntity<Result> getUserLikes(@PathVariable String userId) {
        Result result = userLikeService.getUserLikes(userId);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }


    @GetMapping("/like-rating")
    public ResponseEntity<Result> findRatingByLike() {
        Result result = userLikeService.findRatingBoysAndGirlsByLike();
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }


    @PostMapping("/interests")
    public ResponseEntity<?> findInterests(@RequestBody UserInterestFilterPayload payload,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Find interests payload = {}", payload);
        size = size == 0 ? 10 : size;
        return ResponseEntity.ok(userService.findInterests(payload, size, page));
    }

    @PostMapping("/post")
    public ResponseEntity<Result> savePosts(@RequestBody UserPostReqDTO payload) {
        log.info("Save user posts = {}", payload);
        return ResponseEntity.ok(userPostService.save(payload));
    }

    @PutMapping("/post")
    public ResponseEntity<Result> editPosts(@RequestBody UserPostReqDTO payload) {
        log.info("Edit user posts = {}", payload);
        return ResponseEntity.ok(userPostService.edit(payload));
    }

    @GetMapping("/post")
    public ResponseEntity<Result> getPosts() {
        log.info("Get current user posts ");
        return ResponseEntity.ok(userPostService.getPosts());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Result> getPosts(@PathVariable String id) {
        log.info("Get user posts id={}", id);
        return ResponseEntity.ok(userPostService.getPostsById(id));
    }

    @PutMapping("/language")
    public ResponseEntity<Result> editLanguage(@RequestBody List<Language> payload) {
        log.info("Edit user language = {}", payload);
        return ResponseEntity.ok(userPostService.editLanguage(payload));
    }

    @PostMapping("/story/{mediaId}")
    public ResponseEntity<Result> saveStory(@PathVariable String mediaId) {
        log.info("Save story mediaId = {}", mediaId);
        return ResponseEntity.ok(userStoryService.save(mediaId));
    }

}
