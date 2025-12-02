package com.example.VanishChat.Services;

import com.example.VanishChat.Model.ChatMessage;
import com.example.VanishChat.Repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepo;

    public ChatMessage saveMessage(ChatMessage msg) {
        return chatRepo.save(msg);
    }

    public List<ChatMessage> getChat(String u1, String u2) {
        return chatRepo.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(u1,u2,u1,u2);
    }
}
