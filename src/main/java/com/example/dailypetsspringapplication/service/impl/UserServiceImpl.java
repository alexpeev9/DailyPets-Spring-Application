package com.example.dailypetsspringapplication.service.impl;

import com.example.dailypetsspringapplication.model.binding.UserLoginBM;
import com.example.dailypetsspringapplication.model.binding.UserRegisterBM;
import com.example.dailypetsspringapplication.model.entity.User;
import com.example.dailypetsspringapplication.model.view.UserVM;
import com.example.dailypetsspringapplication.repository.UserRepository;
import com.example.dailypetsspringapplication.service.UserService;
import com.example.dailypetsspringapplication.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final PasswordEncoder encoder;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.encoder = encoder;

    }

    @Override
    public void loginUser(UserLoginBM user) {
        if(currentUser.getId() != null) {
            throw new RuntimeException("User already logged!");
        }
        User userEntity = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(!encoder.matches(user.getPassword(),userEntity.getPassword()))
        {
            throw new RuntimeException("Invalid Credentials!");
        }
        currentUser.setUsername(userEntity.getUsername());
        currentUser.setId(userEntity.getId());
    }

    @Override
    public void registerUser(UserRegisterBM userRegisterBM) {
        User user = modelMapper.map(userRegisterBM, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        currentUser.setUsername(user.getUsername());
        currentUser.setId(user.getId());
    }

    @Override
    public void logout() {
        currentUser.setUsername(null);
        currentUser.setId(null);
    }

    @Override
    public UserVM findById(Long id) {
        return userRepository.findById(id).map(u -> modelMapper.map(u, UserVM.class)).orElse(null);
    }

    @Override
    public boolean isNameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean isLogged() {
        return currentUser.getId() != null;
    }

    @Override
    public User findCurrentUser() {
        return userRepository.findById(currentUser.getId()).orElse(null);
    }
}
