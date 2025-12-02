package com.example.VanishChat.Repository;

import com.example.VanishChat.Model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(
            String sender1, String receiver1, String sender2, String receiver2
    );
}
