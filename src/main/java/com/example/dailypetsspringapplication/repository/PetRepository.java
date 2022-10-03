package com.example.dailypetsspringapplication.repository;

import com.example.dailypetsspringapplication.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
