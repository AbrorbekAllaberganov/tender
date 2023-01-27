package com.example.tender.payload.response;

import com.example.tender.payload.detail.FileForResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostResponse {

    private String userId;
    private List<FileForResponse> photo;
}
