package com.example.dailypetsspringapplication.repository;

import com.example.dailypetsspringapplication.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByName(String name);
    Optional<List<Pet>> searchByNameStartingWith(String name);
}
