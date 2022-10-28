package com.example.dailypetsspringapplication.repository;

import com.example.dailypetsspringapplication.model.entity.Pet;
import com.example.dailypetsspringapplication.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("SELECT p FROM Pet p WHERE p.user = ?1")
    List<Pet> findPetsOfUser(User user);
}
