package com.example.dailypetsspringapplication.service;

import com.example.dailypetsspringapplication.model.binding.PetBM;
import com.example.dailypetsspringapplication.model.view.PetVM;

import java.util.List;

public interface PetService {
    List<PetVM> findAllPetsView();
    void addPet(PetBM petBM);
    PetBM findPet(Long id);
    void updatePet(PetBM petBM);
    void deletePet(Long id);
}
