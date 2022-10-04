package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.model.binding.PetBM;
import com.example.dailypetsspringapplication.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/update/{id}")
    public String updateGET(@PathVariable Long id, Model model) {
        model.addAttribute("pet", petService.findPet(id));
        return "update-pet";
    }

    @PostMapping("/update/{id}")
    public String updatePOST(@Valid PetBM petBM, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", petBM);
            model.addAttribute("org.springframework.validation.BindingResult.pet", bindingResult);

            return "update-pet";
        }

        petService.updatePet(petBM);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteGET(@PathVariable Long id){
        petService.deletePet(id);
        return "redirect:/";
    }

}
