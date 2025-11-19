package com.example.VanishChat.JWToolkit;

import com.example.VanishChat.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // ✅ Public endpoints (skip JWT check)
        if (path.equals("/api/login") ||
                path.equals("/api/register") ||
                path.equals("/login") ||       // Spring login endpoint
                path.equals("/register") ||
                path.equals("/") ||
                path.equals("/Privacy") ||
                path.equals("/verify") ||
                path.equals("/dashboard") ||   // allow dashboard page
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT validation for protected endpoints
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        try {
            String username = jwtService.extractUsername(token);
            if (!jwtService.isTokenValid(token, username)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Token expired or invalid");
                return;
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Session expired or invalid token");
            return;
        }

        // Token valid → continue request
        filterChain.doFilter(request, response);
    }
}
