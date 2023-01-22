package com.example.tender.service;

import com.example.tender.entity.chat.Message;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.payload.request.MessagePayload;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.MessageRepository;
import com.example.tender.repository.UserLikeRepository;
import com.example.tender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatService chatService;

    public Result saveMessage(MessagePayload messagePayload) {
        try {
            Message message = new Message();
            message.setText(messagePayload.getMessage());
            if (messagePayload.getReplyMessageId() != null)
                message.setReplyMessageId(messageRepository.findById(messagePayload.getReplyMessageId()).orElseThrow(
                        () -> new ResourceNotFound("message", "id", messagePayload.getReplyMessageId())
                ));
            message.setFromUser(userRepository.findById(messagePayload.getUserId()).orElseThrow(
                    () -> new ResourceNotFound("user", "id", messagePayload.getUserId())
            ));
            messageRepository.save(message);

            chatService.addMessage(messagePayload.getChatId(), message);

            return Result.success(message);
        } catch (Exception e) {
            return Result.error(e);
        }
    }
}
