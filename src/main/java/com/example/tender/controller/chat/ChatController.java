package com.example.tender.controller.chat;

import com.example.tender.payload.request.ChatPayload;
import com.example.tender.payload.request.MessagePayload;
import com.example.tender.payload.response.Result;
import com.example.tender.repository.ChatRepository;
import com.example.tender.service.ChatService;
import com.example.tender.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;
    private final SimpMessagingTemplate template;

    @PostMapping("/create")
    public ResponseEntity<Result> createChat(@RequestBody ChatPayload chatPayload) {
        Result result = chatService.saveChat(chatPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @PostMapping("/send-message")
    public ResponseEntity<Result> sendMessage(@RequestBody MessagePayload messagePayload) {
        Result result = messageService.saveMessage(messagePayload);
        if (result.isStatus()) {
            template.convertAndSend("/topic/message/" + messagePayload.getChatId(), result.getData());
        }
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @GetMapping("/user-chat/{userId}")
    public ResponseEntity<Result> getChatsByUserId(@PathVariable("userId") String userId) {
        Result result = chatService.getUserChats(userId);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

    @GetMapping("/messsages/{chatId}")
    public ResponseEntity<Result> getMessagesByChatId(@PathVariable("chatId")String chatId){
        Result result = chatService.getMessagesByChatId(chatId);
        return ResponseEntity.status(result.isStatus() ? 200 : 400).body(result);
    }

}

