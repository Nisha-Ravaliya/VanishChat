package com.example.VanishChat.JWToolkit;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "yourSecretKey"; // change this
    private final long JWT_EXPIRATION = 24 * 60 * 60 * 1000; // 24 hours

    // Generate token
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Get username/email from token
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException |
                 ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException ex) {
            return false;
        }
    }
}
