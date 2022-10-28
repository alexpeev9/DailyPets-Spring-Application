package com.example.dailypetsspringapplication.service;

import com.example.dailypetsspringapplication.model.binding.PetBM;
import com.example.dailypetsspringapplication.model.entity.User;
import com.example.dailypetsspringapplication.model.view.PetVM;

import java.util.List;

public interface PetService {
    List<PetVM> findAllPetsView();
    List<PetVM> findSearchedPetsView(String name);
    void addPet(PetBM petBM, User user);
    PetBM findPet(Long id);
    void updatePet(PetBM petBM, User user);
    void deletePet(Long id, User user);
}
