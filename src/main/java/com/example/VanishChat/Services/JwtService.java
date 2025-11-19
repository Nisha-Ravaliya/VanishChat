package com.example.VanishChat.Services;

import com.example.VanishChat.JWToolkit.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private JwtUtil jwtUtil;

    // Extract username from token
    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    // Check if token is valid for username
    public boolean isTokenValid(String token, String username) {
        try {
            String tokenUsername = jwtUtil.extractUsername(token);
            return (username.equals(tokenUsername) && !jwtUtil.isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }
}
