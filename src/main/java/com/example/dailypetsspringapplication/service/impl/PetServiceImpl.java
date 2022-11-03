package com.example.dailypetsspringapplication.service.impl;

import com.example.dailypetsspringapplication.model.binding.PetBM;
import com.example.dailypetsspringapplication.model.entity.Pet;
import com.example.dailypetsspringapplication.model.entity.User;
import com.example.dailypetsspringapplication.model.view.PetVM;
import com.example.dailypetsspringapplication.repository.PetRepository;
import com.example.dailypetsspringapplication.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    private final ModelMapper modelMapper;
    private final PetRepository petRepository;

    public PetServiceImpl(ModelMapper modelMapper, PetRepository petRepository) {
        this.modelMapper = modelMapper;
        this.petRepository = petRepository;
    }

    public List<PetVM> findAllPetsView() {
        return petRepository.findAll().stream().map(p -> modelMapper.map(p, PetVM.class)).collect(Collectors.toList());
    }

    public List<PetVM> findSearchedPetsView(String name) {
        if (name.isEmpty()) throw new RuntimeException("Field cannot be empty!");
        List<PetVM> pets = petRepository.searchByNameStartingWith(name).orElse(null).stream().map(p -> modelMapper.map(p, PetVM.class)).collect(Collectors.toList());
        if (pets.size() == 0) throw new RuntimeException("There are no pets!");
        return pets;
    }

    @Override
    public void addPet(PetBM petBM, User user) {
        validatePetName(petBM.getName());
        Pet pet = modelMapper.map(petBM, Pet.class);
        if (user == null) throw new RuntimeException("User is not logged!");
        pet.setUser(user);
        petRepository.save(pet);
    }

    @Override
    public PetBM findPet(Long id, User user) {
        Pet pet = petRepository.findById(id).orElse(null);
        validatePet(pet, user);
        return modelMapper.map(pet, PetBM.class);
    }

    @Override
    public void updatePet(PetBM petBM, User user) {
        Pet pet = petRepository.findById(petBM.getId()).orElse(null);
        validatePet(pet, user);
        if(!pet.getName().equals(petBM.getName())){
            validatePetName(petBM.getName());
        }
        pet.setName(petBM.getName());
        pet.setDescription(petBM.getDescription());
        pet.setPicture(petBM.getPicture());
        pet.setType(petBM.getType());
        petRepository.save(pet);
    }

    @Override
    public void deletePet(Long id, User user) {
        Pet pet = petRepository.findById(id).orElse(null);
        validatePet(pet, user);
        petRepository.deleteById(pet.getId());
    }

    private void validatePet(Pet pet, User user) {
        if (pet == null) throw new RuntimeException("Pet not found!");
        if (user == null) throw new RuntimeException("User is not logged!");
        if (!pet.getUser().getId().equals(user.getId())) throw new RuntimeException("User must be the creator!");
    }

    private void validatePetName(String name) {
        if (petRepository.findByName(name).orElse(null) != null)
            throw new RuntimeException("Pet name is taken!");
    }
}
