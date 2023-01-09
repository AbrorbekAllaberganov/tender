package com.example.tender.json;
// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SendResult {
    private String message;
    private Long id;
    private String status;
}
