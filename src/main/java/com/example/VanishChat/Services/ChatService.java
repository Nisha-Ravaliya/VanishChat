package com.example.VanishChat.Services;

import com.example.VanishChat.DTO.ChatMessageDto;
import com.example.VanishChat.Model.ChatMessage;
import com.example.VanishChat.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessageDto dto) {
        ChatMessage msg = new ChatMessage();

        msg.setFromUser(dto.getFrom());
        msg.setToUser(dto.getTo());
        msg.setSubject(dto.getSubject());
        msg.setMessage(dto.getMessage());
        msg.setSentAt(Instant.now());

        if (dto.getExpiry() > 0) {
            msg.setExpiresAt(Instant.now().plusMillis(dto.getExpiry()));
        } else {
            msg.setExpiresAt(null); // never expires
        }

        chatMessageRepository.save(msg);
    }

    // Optional: method to clean expired messages (call on schedule)
    public void deleteExpiredMessages() {
        chatMessageRepository.deleteByExpiresAtBefore(Instant.now());
    }
}
