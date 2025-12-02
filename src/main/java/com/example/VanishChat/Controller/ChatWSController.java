package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.ChatMessage;
import com.example.VanishChat.Services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWSController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void send(ChatMessage msg){
        chatService.saveMessage(msg);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + msg.getReceiver(),
                msg
        );
    }
}
