package com.example.dailypetsspringapplication.service;

import com.example.dailypetsspringapplication.model.binding.UserLoginBM;
import com.example.dailypetsspringapplication.model.binding.UserRegisterBM;
import com.example.dailypetsspringapplication.model.entity.User;
import com.example.dailypetsspringapplication.model.view.PetVM;
import com.example.dailypetsspringapplication.model.view.UserVM;

import java.util.List;

public interface UserService {
    UserVM findByUsername(String username);
    UserVM findByEmail(String email);

    User findCurrentUser();

    void registerUser(UserRegisterBM map);

    void loginUser(UserLoginBM user);

    void logout();

    boolean isLogged();

    List<PetVM> findPetsOfUser(String username);
}
