package com.example.VanishChat.Repository;

import com.example.VanishChat.Model.Message;
import com.example.VanishChat.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    void delete(Message msg);
}
