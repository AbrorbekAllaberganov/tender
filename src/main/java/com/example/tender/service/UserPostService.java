package com.example.tender.service;

import com.example.tender.entity.UserPostEntity;
import com.example.tender.entity.users.User;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.payload.detail.FileForResponse;
import com.example.tender.payload.request.user.UserPostReqDTO;
import com.example.tender.payload.response.Result;
import com.example.tender.payload.response.UserPostResponse;
import com.example.tender.repository.UserPostRepository;
import com.example.tender.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Result edit(UserPostReqDTO payload) {
        try {
            if (payload.getPhotos().isEmpty())
                throw new BadRequest("Photos is not empty!");

            Optional<String> currentUser = securityUtils.getCurrentUser();

            if (!currentUser.isPresent())
                throw new BadRequest("User not found!");

            User user = userService.findByPhone(currentUser.get());

            List<UserPostEntity> allPosts = findAllByUserId(user.getId());

            // IdList are exist db.
            List<String> photoIds = allPosts.stream()
                    .map(UserPostEntity::getPhotoId)
                    .collect(Collectors.toList());

            // IdList are not exist db.
            List<String> saveAll = payload.getPhotos().stream()
                    .filter(id -> !photoIds.contains(id))
                    .collect(Collectors.toList());

            // IdList are exist db for delete.
            List<UserPostEntity> deleteAll = allPosts.stream()
                    .filter(id -> !payload.getPhotos().contains(id.getPhotoId()))
                    .collect(Collectors.toList());

            List<UserPostEntity> allList = saveAll.stream().map(photoId -> {
                myFileService.findById(photoId);

                UserPostEntity entity = new UserPostEntity();
                entity.setUserId(user.getId());
                entity.setPhotoId(photoId);
                return entity;
            }).collect(Collectors.toList());

            if (!allList.isEmpty())
                userPostRepository.saveAll(allList);

            if (!deleteAll.isEmpty())
                userPostRepository.deleteAll(deleteAll);

            return Result.success(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }

    public List<UserPostEntity> findAllByUserId(String userId) {
        return userPostRepository.findAllByUserId(userId);
    }

    public Result getPosts() {
        try {
            Optional<String> currentUser = securityUtils.getCurrentUser();

            if (!currentUser.isPresent())
                throw new BadRequest("User not found!");

            User user = userService.findByPhone(currentUser.get());

            List<UserPostEntity> userPostList =
                    findAllByUserId(user.getId());
            List<FileForResponse> responses = userPostList
                    .stream()
                    .map(post -> new FileForResponse(post.getPhotoId(),
                            myFileService.toOpenUrl(post.getPhotoId())))
                    .collect(Collectors.toList());

            return Result.success(new UserPostResponse(user.getId(), responses));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e);
        }
    }
}
