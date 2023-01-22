package com.example.tender.service;

import com.example.tender.entity.chat.Chat;
import com.example.tender.entity.chat.Message;
import com.example.tender.entity.users.User;
import com.example.tender.exceptions.ResourceNotFound;
import com.example.tender.payload.request.ChatPayload;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.ChatRepository;
import com.example.tender.repository.MessageRepository;
import com.example.tender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    public Result saveChat(ChatPayload chatPayload) {
        try {
            Chat chat=new Chat();
            chat.setUser1(findById(chatPayload.getUserId1()));
            chat.setUser2(findById(chatPayload.getUserId2()));
            chatRepository.save(chat);
            return Result.success(chat);
        }catch (Exception e){
            return Result.error(e);
        }
    }

    public Result addMessage(String chatId, Message message){
        try{
            Chat chat=chatRepository.findById(chatId).orElseThrow(
                    ()->new ResourceNotFound("chat","id",chatId)
            );
            List<Message> messageList=chat.getMessageList();
            messageList.add(message);
            chat.setMessageList(messageList);
            chatRepository.save(chat);
            return Result.success(chat);
        }catch (Exception e){
            return Result.error(e);
        }
    }

    public User findById(String userId){
        return userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFound("user","id",userId)
        );
    }

    public Result getMessagesByChatId(String chatId){
        try {
            Chat chat=chatRepository.findById(chatId).orElseThrow(
                    ()->new ResourceNotFound("chat","id",chatId)
            );
            return Result.success(chat.getMessageList());
        }catch (Exception e){
            return Result.error(e);
        }
    }

    public Result getUserChats(String userId){
        return Result.success(chatRepository.getChatsByUserId(userId));
    }

}
