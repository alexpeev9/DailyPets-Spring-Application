package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HomeController {

    private final PetService petService;

    public HomeController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            if (!model.containsAttribute("pets")) {
                model.addAttribute("pets", petService.findAllPetsView());
            }
            return "index";
        } catch (RuntimeException error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("pets", null);
            return "index";
        }
    }

    @PostMapping("/")
    public String search(@Valid String name, RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes
                    .addFlashAttribute("pets", petService.findSearchedPetsView(name));
            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());
            return "redirect:/";
        }
    }
}
