package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.Registration;
import com.example.VanishChat.Repository.RegistrationRepository;
import com.example.VanishChat.JWToolkit.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

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

    // ---------------- REGISTER / LOGIN ----------------
    @PostMapping("/register")
    public ResponseEntity<?> registerOrLoginUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("businessName") String businessName,
            @RequestParam("gstNumber") String gstNumber,
            @RequestParam("address") String address,
            @RequestParam(value = "businessProof", required = false) MultipartFile businessProof) {

        try {
            Optional<Registration> existingUser = repository.findByUsernameOrEmail(username, email);
            Registration user;

            if (existingUser.isPresent()) {
                user = existingUser.get();
            } else {
                user = new Registration();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode(password));
                user.setPhone(phone);
                user.setBusinessName(businessName);
                user.setGstNumber(gstNumber);
                user.setAddress(address);

                if (businessProof != null && !businessProof.isEmpty()) {
                    File uploadFolder = new File(UPLOAD_DIR);
                    if (!uploadFolder.exists()) uploadFolder.mkdirs();

                    String filePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + businessProof.getOriginalFilename();
                    businessProof.transferTo(new File(filePath));
                    user.setProofFilePath(filePath);
                }

                repository.save(user);
            }

            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", existingUser.isPresent() ? "User exists, logging in..." : "Registration successful!",
                    "token", token,
                    "user", user
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

    // ---------------- GET PROFILE ----------------
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("message", "Missing token"));
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);
            Optional<Registration> userOpt = repository.findByUsername(username);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("message", "User not found"));
            }

            Registration user = userOpt.get();
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "password", user.getPassword(), // hashed password returned
                    "phone", user.getPhone(),
                    "businessName", user.getBusinessName(),
                    "gstNumber", user.getGstNumber(),
                    "address", user.getAddress(),
                    "profilePic", user.getProfilePic(),
                    "proofFilePath", user.getProofFilePath()
            ));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body(Map.of("message", "Session expired. Please login again."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    // ---------------- UPDATE PROFILE ----------------
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("businessName") String businessName,
            @RequestParam("gstNumber") String gstNumber,
            @RequestParam("address") String address,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "businessProof", required = false) MultipartFile businessProof) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Missing token"));
        }

        String token = authHeader.substring(7);

        try {
            String currentUsername = jwtUtil.extractUsername(token);
            Optional<Registration> userOpt = repository.findByUsername(currentUsername);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("success", false, "message", "User not found"));
            }

            Registration user = userOpt.get();
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);
            user.setBusinessName(businessName);
            user.setGstNumber(gstNumber);
            user.setAddress(address);

            if (password != null && !password.isEmpty()) {
                user.setPassword(passwordEncoder.encode(password));
            }

            if (businessProof != null && !businessProof.isEmpty()) {
                File uploadFolder = new File(UPLOAD_DIR);
                if (!uploadFolder.exists()) uploadFolder.mkdirs();

                String filePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + businessProof.getOriginalFilename();
                businessProof.transferTo(new File(filePath));
                user.setProofFilePath(filePath);
            }

            repository.save(user); // could throw DataIntegrityViolationException if email/username duplicate

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Profile updated successfully",
                    "user", user
            ));

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 print full exception for debugging
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Error: " + e.getMessage()));
        }
    }

}
