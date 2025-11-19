package com.example.VanishChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

public class VanishChatApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(VanishChatApplication.class, args);
	}

}



