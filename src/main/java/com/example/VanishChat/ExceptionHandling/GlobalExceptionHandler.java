package com.example.VanishChat.ExceptionHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(Map.of(
                        "success", false,
                        "message", "Internal Server Error: " + ex.getMessage()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleAccessDenied(RuntimeException ex) {
        if (ex.getMessage().contains("Access Denied")) {
            return "redirect:/admin/login";
        }
        return "error"; // generic error page
    }
}