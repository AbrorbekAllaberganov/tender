package com.example.tender.service;

import com.example.tender.entity.UserLike;
import com.example.tender.entity.UserPostEntity;
import com.example.tender.entity.enums.LikeType;
import com.example.tender.entity.users.User;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.payload.request.user.UserPostReqDTO;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.UserPostRepository;
import com.example.tender.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 'Mukhtarov Sarvarbek' on 19.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserPostService {

    private final UserService userService;
    private final MyFileService myFileService;
    private final SecurityUtils securityUtils;

    private final UserPostRepository userPostRepository;

    public Result save(UserPostReqDTO payload) {
        try {
            if (payload.getPhotos().isEmpty())
                throw new BadRequest("Photos is not empty!");

            Optional<String> currentUser = securityUtils.getCurrentUser();

            if (!currentUser.isPresent())
                throw new BadRequest("User not found!");

            User user = userService.findByPhone(currentUser.get());

            List<UserPostEntity> allList = payload.getPhotos().stream().map(photoId -> {
                myFileService.findById(photoId);

                UserPostEntity entity = new UserPostEntity();
                entity.setUserId(user.getId());
                entity.setPhotoId(photoId);
                return entity;
            }).collect(Collectors.toList());

            userPostRepository.saveAll(allList);

            return Result.success(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }
}
