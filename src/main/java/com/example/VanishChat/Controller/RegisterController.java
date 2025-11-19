package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.Registration;
import com.example.VanishChat.Repository.RegistrationRepository;
import com.example.VanishChat.JWToolkit.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegisterController {

    private static final String UPLOAD_DIR = "D:/Spring/VanishChat/uploads/";

    @Autowired
    private RegistrationRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerOrLoginUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("businessName") String businessName,
            @RequestParam("gstNumber") String gstNumber,
            @RequestParam("address") String address,
            @RequestParam("businessProof") MultipartFile businessProof) {

        try {
            // Check if user already exists
            var existingUser = repository.findByUsernameOrEmail(username, email);
            Registration user;

            if (existingUser.isPresent()) {
                // ✅ User exists, just use it for login
                user = existingUser.get();
            } else {
                // ✅ Create upload folder if not exists
                File uploadFolder = new File(UPLOAD_DIR);
                if (!uploadFolder.exists()) uploadFolder.mkdirs();

                // ✅ Save uploaded file
                String filePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + businessProof.getOriginalFilename();
                businessProof.transferTo(new File(filePath));

                // ✅ Hash password
                String hashedPassword = passwordEncoder.encode(password);

                // ✅ Create new registration object
                user = new Registration();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(hashedPassword);
                user.setBusinessName(businessName);
                user.setGstNumber(gstNumber);
                user.setAddress(address);
                user.setProofFilePath(filePath);

                // ✅ Save in DB
                repository.save(user);
            }

            // ✅ Generate JWT Token for dashboard access
            String token = jwtUtil.generateToken(user.getUsername());

            // ✅ Return JSON response
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", existingUser.isPresent() ?
                            "User already registered. Logging in..." :
                            "Registration successful! Redirecting to dashboard...",
                    "token", token
            ));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "File upload error: " + e.getMessage()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Unexpected error: " + ex.getMessage()
            ));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload) {
        String usernameOrEmail = payload.get("usernameOrEmail");
        String password = payload.get("password");

        var userOpt = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "User not found"));
        }

        Registration user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid password"));
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login successful"
        ));
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("message", "Missing or invalid token"));
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);

            var userOpt = repository.findByUsernameOrEmail(username, username);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("message", "User not found"));
            }

            Registration user = userOpt.get();

            return ResponseEntity.ok(Map.of(
                    "name", user.getUsername(),
                    "email", user.getEmail(),
                    "businessName", user.getBusinessName(),
                    "address", user.getAddress()
            ));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body(Map.of("message", "Session expired. Please login again."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error: " + e.getMessage()));
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> payload) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Missing token"));
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        var userOpt = repository.findByUsernameOrEmail(username, username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "User not found"));
        }

        Registration user = userOpt.get();
        user.setUsername(payload.get("username"));
        user.setEmail(payload.get("email"));
        user.setBusinessName(payload.get("businessName"));
        user.setAddress(payload.get("address"));

        repository.save(user);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "name", user.getUsername(),
                "email", user.getEmail(),
                "businessName", user.getBusinessName(),
                "address", user.getAddress()
        ));
    }


}

