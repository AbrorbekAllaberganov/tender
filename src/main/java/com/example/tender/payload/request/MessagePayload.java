package com.example.tender.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessagePayload {
    String message;
    String replyMessageId;
    String userId;
    String chatId;
}
