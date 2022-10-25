package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.model.binding.UserLoginBM;
import com.example.dailypetsspringapplication.model.binding.UserRegisterBM;
import com.example.dailypetsspringapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterBM userRegisterBM() {
        return new UserRegisterBM();
    }

    @ModelAttribute
    public UserLoginBM userLoginBM(){
        return new UserLoginBM();
    }

    @GetMapping("/register")
    public String registerGET(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerPOST(@Valid UserRegisterBM userRegisterBM,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userRegisterBM.getPassword().equals(userRegisterBM.getConfirmPassword())) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBM", userRegisterBM);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBM", bindingResult);

            return "redirect:register";
        }

        userService.registerUser(userRegisterBM);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginGET(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginPOST(@Valid UserLoginBM userLoginBM,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userLoginBM", userLoginBM);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBM", bindingResult);

            return "redirect:login";
        }

        userService.loginUser(userLoginBM);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }

    @GetMapping("/profile/{id}")
    private String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "profile";
    }
}
