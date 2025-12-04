package com.example.VanishChat.DTO;

import com.example.VanishChat.Model.Registration;

public class LoginResponse {

    private String token;
    private Registration user;

    public LoginResponse(String token, Registration user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() { return token; }
    public Registration getUser() { return user; }
}
