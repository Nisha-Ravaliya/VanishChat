package com.example.VanishChat.Controller;

import com.example.VanishChat.Model.User;
import com.example.VanishChat.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserRepository userRepo;

    // Register
    @PostMapping("/register")
    public User register(@RequestBody User u){
        return userRepo.save(u);
    }

    // Login
    @PostMapping("/login")
    public User login(@RequestBody User u){
        Optional<User> optionalUser = userRepo.findByEmail(u.getEmail());
        if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(u.getPassword())){
            return optionalUser.get(); // logged-in user data
        }
        return null; // invalid login
    }

    // Get user profile by email
    @GetMapping("/profile/{email}")
    public User getProfile(@PathVariable String email){
        return userRepo.findByEmail(email).orElse(null);
    }

    // Update profile
    @PostMapping("/profile/update")
    public User updateProfile(@RequestBody User u){
        Optional<User> optionalUser = userRepo.findByEmail(u.getEmail());
        if(optionalUser.isPresent()){
            User existing = optionalUser.get();
            existing.setUsername(u.getUsername());
            existing.setPhone(u.getPhone());
            existing.setCompany(u.getCompany());
            existing.setCity(u.getCity());
            existing.setGstNumber(u.getGstNumber());
            existing.setAddress(u.getAddress());
            existing.setFilePath(u.getFilePath());
            return userRepo.save(existing);
        }
        return null;
    }
}
