package com.example.VanishChat.Repository;

import com.example.VanishChat.Model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Find messages by fromUser and toUser and not expired yet
    List<ChatMessage> findByFromUserAndToUserAndExpiresAtAfter(String fromUser, String toUser, Instant now);

    // Delete expired messages
    void deleteByExpiresAtBefore(Instant now);
}
