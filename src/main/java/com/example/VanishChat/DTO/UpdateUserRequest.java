package com.example.VanishChat.DTO;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String username;
    private String businessName;
    private String gstNumber;
    private String address;
}
