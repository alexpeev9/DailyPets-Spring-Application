package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PetService petService;

    public HomeController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pets", petService.findAllPetsView());
        return "index";
    }
}
