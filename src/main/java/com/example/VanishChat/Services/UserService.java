//package com.example.VanishChat.Services;
//
//public class UserService {
//    public User updateUser(String email, UpdateUserRequest req) {
//        User user = repo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        user.setUsername(req.getUsername());
//        user.setBusinessName(req.getBusinessName());
//        user.setGstNumber(req.getGstNumber());
//        user.setAddress(req.getAddress());
//
//        return repo.save(user);
//    }
//
//}
