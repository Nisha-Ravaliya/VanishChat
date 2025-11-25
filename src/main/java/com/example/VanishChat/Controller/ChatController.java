package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.Message;
import com.example.VanishChat.Repository.DashboardRepository;
import com.example.VanishChat.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private DashboardRepository dashboardRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String,String> payload) {
        String from = payload.get("from");
        String to = payload.get("to");
        String content = payload.get("content");

        long expiryMinutes;
        try {
            expiryMinutes = Long.parseLong(payload.get("expiryMinutes"));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid expiry time");
        }

        if(!dashboardRepo.existsById(to)) return ResponseEntity.badRequest().body("Recipient not found");

        Message msg = new Message();
        msg.setFromEmail(from);
        msg.setToEmail(to);
        msg.setContent(content);
        msg.setSentTime(LocalDateTime.now());
        msg.setExpiryTime(LocalDateTime.now().plusMinutes(expiryMinutes));
        msg.setReadFlag(false);

        messageRepo.save(msg);

        // Real-time notification via WebSocket
        messagingTemplate.convertAndSend("/topic/" + to, msg);

        return ResponseEntity.ok(msg);
    }

    @GetMapping("/messages/{email}")
    public List<Message> getMessages(@PathVariable String email){
        return messageRepo.findByFromEmailOrToEmail(email, email);
    }

    @Scheduled(fixedRate = 60000)
    public void deleteExpiredMessages() {
        messageRepo.deleteByExpiryTimeBefore(LocalDateTime.now());
    }
}
