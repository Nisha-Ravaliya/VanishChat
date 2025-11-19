package com.example.VanishChat.Controller;

import com.example.VanishChat.DTO.ChatMessageDto;
import com.example.VanishChat.Services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody ChatMessageDto chatMessageDto) {
        try {
            chatService.saveMessage(chatMessageDto);
            return ResponseEntity.ok(Map.of("success", true, "message", "Message sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
