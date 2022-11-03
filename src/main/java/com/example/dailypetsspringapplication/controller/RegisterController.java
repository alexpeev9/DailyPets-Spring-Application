package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.model.binding.UserRegisterBM;
import com.example.dailypetsspringapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterBM userRegisterBM() {
        return new UserRegisterBM();
    }

    @GetMapping("/register")
    public String registerGET(RedirectAttributes redirectAttributes) {
        try {
            if (userService.isLogged()) throw new RuntimeException("User is already logged!");
            return "register";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/register")
    public String registerPOST(@Valid UserRegisterBM userRegisterBM,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors() || !userRegisterBM.getPassword().equals(userRegisterBM.getConfirmPassword())) {
                redirectAttributes
                        .addFlashAttribute("userRegisterBM", userRegisterBM);
                redirectAttributes
                        .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBM", bindingResult);

                return "redirect:register";
            }

            userService.registerUser(userRegisterBM);
            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBM", userRegisterBM);
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());
            return "redirect:register";
        }
    }
}
