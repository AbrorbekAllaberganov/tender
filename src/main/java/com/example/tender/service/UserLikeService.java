package com.example.tender.service;

import com.example.tender.entity.UserLike;
import com.example.tender.entity.enums.LikeType;
import com.example.tender.entity.users.User;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.payload.Result;
import com.example.tender.payload.UserLikePayload;
import com.example.tender.repository.UserLikeRepository;
import com.example.tender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLikeService {
    private final UserLikeRepository userLikeRepository;
    private final UserRepository userRepository;

    public Result saveUserLike(UserLikePayload userLikePayload) {
        try {
            UserLike userLike = new UserLike();
            userLike.setToUser(findUserById(userLikePayload.getToUserId()));
            userLike.setFromUser(findUserById(userLikePayload.getFromUserId()));
            userLike.setType(LikeType.LIKE);
            userLikeRepository.save(userLike);
            return Result.success(userLike);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public Result getUserLikes(String userId) {
        return Result.success(userLikeRepository.findAllByToUser_IdOrderByCreatedAt(userId));
    }

    public Result changeStatus(String userLikeId) {
        try {
            UserLike userLike = userLikeRepository.findById(userLikeId).orElseThrow(
                    () -> new ResourceNotFound("userLike", "id", userLikeId)
            );
            switch (userLike.getType().name()) {
                case "LIKE":
                    userLike.setType(LikeType.SUPER_LIKE);
                    break;
                case "SUPER_LIKE":
                    userLike.setType(LikeType.LIKE);
                    break;
            }
            userLikeRepository.save(userLike);
            return Result.success(userLike);
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFound("user", "id", userId)
        );
    }
}
