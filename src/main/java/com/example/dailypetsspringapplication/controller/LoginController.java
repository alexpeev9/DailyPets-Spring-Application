package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.model.binding.UserLoginBM;
import com.example.dailypetsspringapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserLoginBM userLoginBM() {
        return new UserLoginBM();
    }

    @GetMapping("/login")
    public String loginGET(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginPOST(@Valid UserLoginBM userLoginBM,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes
                        .addFlashAttribute("userLoginBM", userLoginBM);
                redirectAttributes
                        .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBM", bindingResult);

                return "redirect:login";
            }

            userService.loginUser(userLoginBM);
            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("userLoginBM", userLoginBM);
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());
            return "redirect:login";
        }
    }
}
