package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.User;
import com.example.VanishChat.Model.ChatMessage;
import com.example.VanishChat.Repository.UserRepository;
import com.example.VanishChat.Services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApiController {

    private final UserRepository userRepo;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // USER APIs
    @PostMapping("/register")
    public User register(@RequestBody User u) {
        return userRepo.save(u);
    }

    @PostMapping("/login")
    public User login(@RequestBody User u){
        Optional<User> optionalUser = userRepo.findByEmail(u.getEmail()); // email के लिए repo method
        return optionalUser.orElse(null);
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // CHAT APIs
    @PostMapping("/chat/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage msg) {
        ChatMessage saved = chatService.saveMessage(msg);
        // Send via WebSocket to receiver
        messagingTemplate.convertAndSend("/topic/chat/" + msg.getReceiver(), saved);
        return saved;
    }

    @GetMapping("/chat/{u1}/{u2}")
    public List<ChatMessage> getChats(@PathVariable String u1, @PathVariable String u2) {
        return chatService.getChat(u1, u2);
    }
}
