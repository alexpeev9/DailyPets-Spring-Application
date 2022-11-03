package com.example.dailypetsspringapplication.controller;

import com.example.dailypetsspringapplication.model.binding.PetBM;
import com.example.dailypetsspringapplication.service.PetService;
import com.example.dailypetsspringapplication.service.UserService;
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
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @ModelAttribute
    public PetBM petBM() {
        return new PetBM();
    }

    @GetMapping("/add")
    public String addGET(RedirectAttributes redirectAttributes) {
        try {
            if (!userService.isLogged()) throw new RuntimeException("User is not logged!");
            return "add-pet";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/add")
    public String addPOST(@Valid PetBM petBM, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("petBM", petBM);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.petBM", bindingResult);
                return "redirect:add";
            }
            petService.addPet(petBM, userService.findCurrentUser());
            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("petBM", petBM);
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());
            return "redirect:add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateGET(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("petBM", petService.findPet(id, userService.findCurrentUser()));
            return "update-pet";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/update/{id}")
    public String updatePOST(@Valid PetBM petBM, BindingResult bindingResult, Model model) throws IOException {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("petBM", petBM);
                model.addAttribute("org.springframework.validation.BindingResult.pet", bindingResult);
                return "update-pet";
            }
            petService.updatePet(petBM, userService.findCurrentUser());
            return "redirect:/";
        } catch (RuntimeException error) {
            model.addAttribute("petBM", petBM);
            model.addAttribute("error", error.getMessage());
            return "update-pet";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGET(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            petService.deletePet(id, userService.findCurrentUser());
            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
            return "redirect:/";
        }
    }
}
