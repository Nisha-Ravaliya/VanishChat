//package com.example.VanishChat.Controller;
//
//import com.example.VanishChat.Model.Message;
//import com.example.VanishChat.Services.DashboardUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    @Autowired
//    private DashboardUserService userService;
//
//    @MessageMapping("/sendMessage") // incoming messages
//    @SendTo("/topic/messages")      // broadcast messages
//    public Message sendMessage(Message message) {
//        // Save message in DB
//        userService.sendMessage(message.getFrom(), message.getTo(), message.getContent());
//        return message; // Send back to subscribers
//    }
//}
