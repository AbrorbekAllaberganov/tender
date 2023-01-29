package com.example.tender.payload.response;

import com.example.tender.payload.detail.FileForResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 'Mukhtarov Sarvarbek' on 29.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStoryResponse {
    private FileForResponse media;
}
