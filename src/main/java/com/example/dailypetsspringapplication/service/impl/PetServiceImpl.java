package com.example.dailypetsspringapplication.service.impl;

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
}
