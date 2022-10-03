package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.model.binding.PetBM;
import com.example.dailypetsspringapplication.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @ModelAttribute
    public PetBM petBM() {
        return new PetBM();
    }

    @GetMapping("/add")
    public String addGET() {
        return "add-pet";
    }

    @PostMapping("/add")
    public String addPOST(@Valid PetBM petBM, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("petBM", petBM);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.petBM", bindingResult);

            return "redirect:add";
        }

        petService.addPet(petBM);
        return "redirect:/";
    }
}
