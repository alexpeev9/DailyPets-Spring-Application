package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        try {
            userService.logout();
            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, Model model) {
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
