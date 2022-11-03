package com.example.dailypetsspringapplication.service.impl;

import com.example.dailypetsspringapplication.model.binding.UserLoginBM;
import com.example.dailypetsspringapplication.model.binding.UserRegisterBM;
import com.example.dailypetsspringapplication.model.entity.User;
import com.example.dailypetsspringapplication.model.view.PetVM;
import com.example.dailypetsspringapplication.model.view.UserVM;
import com.example.dailypetsspringapplication.repository.UserRepository;
import com.example.dailypetsspringapplication.service.UserService;
import com.example.dailypetsspringapplication.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if (currentUser.getId() != null) {
            throw new RuntimeException("User already logged!");
        }
        User userEntity = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (userEntity == null)
            throw new RuntimeException("User not found!");
        if (!encoder.matches(user.getPassword(), userEntity.getPassword()))
            throw new RuntimeException("Invalid Credentials!");

        currentUser.setUsername(userEntity.getUsername());
        currentUser.setId(userEntity.getId());
    }

    @Override
    public void registerUser(UserRegisterBM userRegisterBM) {
        if (currentUser.getId() != null)
            throw new RuntimeException("User already logged!");
        if (findByUsername(userRegisterBM.getUsername()) != null)
            throw new RuntimeException("Username is taken!");
        if (findByEmail(userRegisterBM.getEmail()) != null)
            throw new RuntimeException("Email is taken!");

        User user = modelMapper.map(userRegisterBM, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        currentUser.setUsername(user.getUsername());
        currentUser.setId(user.getId());
    }

    @Override
    public void logout() {
        if (!this.isLogged()) throw new RuntimeException("User is not logged!");
        currentUser.setUsername(null);
        currentUser.setId(null);
    }

    @Override
    public UserVM findById(Long id) {
        return userRepository.findById(id).map(u -> modelMapper.map(u, UserVM.class)).orElse(null);
    }

    @Override
    public UserVM findByEmail(String email) {
        return userRepository.findByEmail(email).map(u -> modelMapper.map(u, UserVM.class)).orElse(null);
    }

    @Override
    public UserVM findByUsername(String username) {
        return userRepository.findByUsername(username).map(u -> modelMapper.map(u, UserVM.class)).orElse(null);
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
        if (!this.isLogged()) throw new RuntimeException("User is not logged!");
        User user = userRepository.findById(currentUser.getId()).orElse(null);
        if (user == null) throw new RuntimeException("User is not found!");
        return user;
    }

    @Override
    public List<PetVM> findPetsOfUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) throw new RuntimeException("User not found!");
        List<PetVM> pets = userRepository.findPetsOfUser(user).stream().map(p -> modelMapper.map(p, PetVM.class)).collect(Collectors.toList());
        if (pets.stream().count() == 0) return null;
        return pets;
    }
}
