// src/main/java/com/example/vanishchat/controller/PageController.java
package com.example.VanishChat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PageController {


    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }

    @GetMapping("/verify")
    public String verify() {
        return "verify"; //templates/verify.html
    }

    @GetMapping("vanish_chat_demo")
    public String vanishChatDemo(Model model, Principal principal) {
        boolean isLoggedIn = principal != null;
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "vanish_chat_demo";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; //  returns register.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Spring will look for dashboard.html in templates/
    }

    @GetMapping("/login")
    public String login() {
        return "login"; //  templates/login.html
    }

    @GetMapping("/edit_profile")
    public String editProfilePage() {
        return "edit_profile"; // templates/edit_profile.html
    }

    @GetMapping("/Privacy")
    public String Privacy() {
        return "Privacy"; //  templates/Privacy.html
    }

}




