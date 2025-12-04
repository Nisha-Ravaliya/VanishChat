package com.example.VanishChat.Controller;

import com.example.VanishChat.DTO.LoginRequest;
import com.example.VanishChat.DTO.LoginResponse;
import com.example.VanishChat.Model.Registration;
import com.example.VanishChat.Repository.RegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private RegistrationRepository registrationRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // Repository method should return Optional<Registration>
        Optional<Registration> existingUser =
                registrationRepository.findByEmailOrUsername(
                        request.getUsernameOrEmail(),
                        request.getUsernameOrEmail()
                );

        if (existingUser.isEmpty()) {
            return ResponseEntity.status(401).body(
                    java.util.Map.of("message", "Invalid username/email or password")
            );
        }

        Registration user = existingUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(
                    java.util.Map.of("message", "Invalid username/email or password")
            );
        }

        String token = "fake-jwt-" + user.getId();

        return ResponseEntity.ok(new LoginResponse(token, user));
    }
}
