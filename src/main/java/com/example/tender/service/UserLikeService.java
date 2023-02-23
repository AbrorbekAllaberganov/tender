package com.example.tender.service;

import com.example.tender.entity.UserLike;
import com.example.tender.entity.enums.LikeType;
import com.example.tender.entity.users.User;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.payload.request.UserLikePayload;
import com.example.tender.payload.response.LikeRatingResDTO;
import com.example.tender.payload.response.Result;
import com.example.tender.payload.response.UserShortInfoResDTO;
import com.example.tender.repository.UserLikeRepository;
import com.example.tender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLikeService {
    private final UserLikeRepository userLikeRepository;
    private final UserRepository userRepository;
    private final MyFileService myFileService;

    public LikeType getLikeType(String like) {
        switch (like) {
            case "LIKE":
                return LikeType.LIKE;
            case "DIS_LIKE":
                return LikeType.DIS_LIKE;
            case "SUPER_LIKE":
                return LikeType.SUPER_LIKE;
        }

        throw new BadRequest("Type is undefined");
    }

    public Result saveUserLike(UserLikePayload userLikePayload) {
        try {
            UserLike userLike = new UserLike();
            userLike.setToUser(findUserById(userLikePayload.getToUserId()));
            userLike.setFromUser(findUserById(userLikePayload.getFromUserId()));
            userLike.setType(getLikeType(userLikePayload.getType()));
            userLike.setActive(true);

            userLikeRepository.save(userLike);
            return Result.success(userLike);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public Result getUserLikes(String userId) {
        return Result.success(userLikeRepository.getUserLikes(userId));
    }

    public Result changeStatus(String userLikeId, String like) {
        try {
            UserLike userLike = userLikeRepository.findById(userLikeId).orElseThrow(
                    () -> new ResourceNotFound("userLike", "id", userLikeId));
            userLike.setActive(false);
            if (like.equals("LIKE")) {
                userLikeRepository.save(new UserLike(userLike.getFromUser(), userLike.getToUser(), false, LikeType.SUPER_LIKE));
                userLike.setType(LikeType.SUPER_LIKE);
            } else {
                userLikeRepository.save(new UserLike(userLike.getFromUser(), userLike.getToUser(), false, LikeType.DIS_LIKE));
            }
            userLikeRepository.save(userLike);

            return Result.success(userLike);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFound("user", "id", userId)
        );
    }

    public Result findRatingBoysAndGirlsByLike() {

        List<UserShortInfoResDTO> boys = userLikeRepository
                .findRatingByLike(true)
                .stream()
                .map(mapper -> {
                    UserShortInfoResDTO dto = UserShortInfoResDTO.toDTO(mapper);
                    dto.setPhotoUrl(myFileService.toOpenUrl(mapper.getPhotoId()));
                    return dto;
                })
                .collect(Collectors.toList());

        List<UserShortInfoResDTO> girls = userLikeRepository
                .findRatingByLike(false)
                .stream()
                .map(mapper -> {
                    UserShortInfoResDTO dto = UserShortInfoResDTO.toDTO(mapper);
                    dto.setPhotoUrl(myFileService.toOpenUrl(mapper.getPhotoId()));
                    return dto;
                })
                .collect(Collectors.toList());

        return Result.success(new LikeRatingResDTO(boys, girls));
    }


}
