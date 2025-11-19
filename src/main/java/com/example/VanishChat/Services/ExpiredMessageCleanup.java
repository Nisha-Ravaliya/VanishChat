package com.example.VanishChat.Services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredMessageCleanup {

    private final ChatService chatService;

    public ExpiredMessageCleanup(ChatService chatService) {
        this.chatService = chatService;
    }

    // Run cleanup every hour
    @Scheduled(fixedRate = 3600000)
    public void cleanup() {
        chatService.deleteExpiredMessages();
    }
}
