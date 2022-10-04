package com.example.dailypetsspringapplication.repository;

import com.example.dailypetsspringapplication.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("DELETE FROM Pet p WHERE p.id = ?1")
    void delete(Long id);
}
