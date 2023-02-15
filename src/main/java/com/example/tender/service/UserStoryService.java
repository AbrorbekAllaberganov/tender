package com.example.tender.service;

import com.example.tender.entity.MyFile;
import com.example.tender.entity.users.User;
import com.example.tender.entity.users.UserStory;
import com.example.tender.exceptions.BadRequest;
import com.example.tender.payload.detail.FileForResponse;
import com.example.tender.payload.response.Result;
import com.example.tender.payload.response.UserStoryResponse;
import com.example.tender.repository.MessageRepository;
import com.example.tender.repository.UserRepository;
import com.example.tender.repository.UserStoryRepository;
import com.example.tender.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final UserService userService;
    private final MyFileService myFileService;
    private final SecurityUtils securityUtils;
    private final MessageRepository messageRepository;


    public Result save(final String mediaId) {
        try {
            Optional<String> currentUser = securityUtils.getCurrentUser();

            if (!currentUser.isPresent())
                throw new BadRequest("User not found!");

            User user = userService.findByPhone(currentUser.get());
            MyFile media = myFileService.findById(mediaId);
            UserStory userStory = new UserStory();
            userStory.setUserId(user.getId());
            userStory.setMediaId(mediaId);

            userStoryRepository.save(userStory);

            return Result.success(new UserStoryResponse(
                    new FileForResponse(mediaId,
                            myFileService.toOpenUrl(media))
                    )
            );
        } catch (Exception e) {
            log.error("User story save exception = {}", e.getMessage());
            return Result.error(e, e.getMessage());
        }
    }

    @Scheduled(fixedRate = 4000)
    public void storyRemover(){
        try {
            LocalDateTime now = LocalDateTime.now().minusHours(24);
            List<UserStory> userStoryList=userStoryRepository.findAllByCreatedAtBefore(now);
            for (UserStory userStory : userStoryList) {
                messageRepository.updateMessageStoryToNull(userStory.getId());
            }
            userStoryRepository.deleteAllByCreatedAtBefore(now);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
