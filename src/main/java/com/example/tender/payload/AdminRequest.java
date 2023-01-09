package com.example.tender.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRequest {
    private String username;

    private String fullname;

    private String email;

    private String password;

    private String phoneNumber;

}
