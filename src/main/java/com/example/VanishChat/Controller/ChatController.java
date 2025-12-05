package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;

    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    // Email को safe channel format में बदलने वाला method
    private String encodeEmail(String email) {
        return email.replace(".", "_dot_").replace("@", "_at_");
    }

    @MessageMapping("/send")
    public void sendMessage(ChatMessage msg) {

        // Receiver का सुरक्षित channel
        String safeReceiver = encodeEmail(msg.getReceiver());

        // sender का safe email भी चाहिए notification के लिए
        String safeSender = encodeEmail(msg.getSender());

        // MESSAGE भेजो
        template.convertAndSend("/chat/" + safeReceiver, msg);

        // NOTIFICATION भेजो
        template.convertAndSend("/notify/" + safeReceiver,
                "New message from " + msg.getSender());
    }
}
