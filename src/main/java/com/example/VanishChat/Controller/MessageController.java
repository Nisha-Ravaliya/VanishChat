package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.Message;
import com.example.VanishChat.Services.MessageService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message msg) {
        if (msg.getExpireAfterSeconds() == null)
            msg.setExpireAfterSeconds(10); // default vanish time
        return service.saveMessage(msg);
    }

    @GetMapping
    public List<Message> getMessages() {
        return service.getAllMessages();
    }
}
