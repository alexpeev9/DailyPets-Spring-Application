package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }

    @GetMapping("/profile/{username}")
    private String profile(@PathVariable String username, Model model) {
        try {
            model.addAttribute("user", userService.findByUsername(username));
            model.addAttribute("pets", userService.findPetsOfUser(username));
            return "profile";
        } catch (RuntimeException error) {
            model.addAttribute("error", error.getMessage());
            return "profile";
        }
    }
}
