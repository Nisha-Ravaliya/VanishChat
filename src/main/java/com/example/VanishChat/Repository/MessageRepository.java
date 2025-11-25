package com.example.VanishChat.Repository;

import com.example.VanishChat.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByFromEmailOrToEmail(String from, String to);
    void deleteByExpiryTimeBefore(LocalDateTime now);
}
