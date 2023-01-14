package com.example.tender.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLikePayload {
    String toUserId;
    String fromUserId;
    String type;
}
