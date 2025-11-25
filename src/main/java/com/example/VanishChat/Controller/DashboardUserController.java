//package com.example.VanishChat.Controller;
//
//import com.example.VanishChat.Model.DashboardUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin
//public class DashboardUserController {
//
//    @Autowired
//    private DashboardUserService dashboardUserService;
//
//    // GET user by email
//    @GetMapping("/{email}")
//    public DashboardUser getUser(@PathVariable String email) {
//        return dashboardUserService.getUser(email);
//    }
//
//    // CREATE new user
//    @PostMapping("/create")
//    public DashboardUser createUser(@RequestBody DashboardUser user) {
//        dashboardUserService.addUser(user);
//        return user;
//    }
//
//    // GET all users
//    @GetMapping("/all")
//    public List<DashboardUser> getAllUsers() {
//        return dashboardUserService.getAllUsers();
//    }
//}
