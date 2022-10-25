package com.example.dailypetsspringapplication.service;

import com.example.dailypetsspringapplication.model.binding.UserLoginBM;
import com.example.dailypetsspringapplication.model.binding.UserRegisterBM;
import com.example.dailypetsspringapplication.model.entity.User;
import com.example.dailypetsspringapplication.model.view.UserVM;

public interface UserService {
    UserVM findById(Long id);

    User findCurrentUser();

    void registerUser(UserRegisterBM map);

    void loginUser(UserLoginBM user);

    void logout();

    boolean isLogged();

    boolean isNameExists(String username);
}
