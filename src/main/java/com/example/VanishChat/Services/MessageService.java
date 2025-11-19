package com.example.VanishChat.Services;

import com.example.VanishChat.Model.Message;
import com.example.VanishChat.Repository.MessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repo;

    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    public Message saveMessage(Message msg) {
        return repo.save(msg);
    }

    public List<Message> getAllMessages() {
        return repo.findAll();
    }

    // 🔥 Delete expired messages every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void deleteExpiredMessages() {
        List<Message> messages = repo.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Message msg : messages) {
            if (msg.getExpireAfterSeconds() != null) {
                LocalDateTime expiryTime = msg.getCreatedAt().plusSeconds(msg.getExpireAfterSeconds());
                if (expiryTime.isBefore(now)) {
                    repo.delete(msg);
                    System.out.println("🕒 Deleted expired message ID: " + msg.getId());
                }
            }
        }
    }
}
