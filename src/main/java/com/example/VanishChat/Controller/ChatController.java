package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    // In-memory chat storage (can move to DB later)
    private final Map<String, List<ChatMessage>> chats = new HashMap<>();

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public void sendMessage(ChatMessage msg) {

        msg.setTimestamp(System.currentTimeMillis());

        // Store chat
        String key = msg.getSender() + "_" + msg.getReceiver();
        chats.putIfAbsent(key, new ArrayList<>());
        chats.get(key).add(msg);

        // Send message to receiver live
        messagingTemplate.convertAndSend("/chat/" + msg.getReceiver(), msg);

        // Send notification to receiver dashboard
        messagingTemplate.convertAndSend("/notify/" + msg.getReceiver(),
                msg.getSender() + " sent a message");
    }

    // Fetch chat history
    @GetMapping("/chat/history/{u1}/{u2}")
    public List<ChatMessage> getChatHistory(@PathVariable String u1, @PathVariable String u2) {
        List<ChatMessage> data = new ArrayList<>();
        String k1 = u1 + "_" + u2;
        String k2 = u2 + "_" + u1;

        if (chats.containsKey(k1)) data.addAll(chats.get(k1));
        if (chats.containsKey(k2)) data.addAll(chats.get(k2));

        data.sort(Comparator.comparing(ChatMessage::getTimestamp));
        return data;
    }
}
