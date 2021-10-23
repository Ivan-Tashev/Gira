package com.example.gira.service.impl;

import com.example.gira.model.binding.UserLoginBindModel;
import com.example.gira.model.entity.UserEntity;
import com.example.gira.model.service.UserServiceModel;
import com.example.gira.repo.UserRepo;
import com.example.gira.security.CurrentUser;
import com.example.gira.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepo userRepo, CurrentUser currentUser, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean checkExistsByUsername(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);
        UserEntity savedUser = userRepo.save(userEntity);
        return modelMapper.map(savedUser, UserServiceModel.class);
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<UserEntity> userEntityOpt = userRepo.findByEmail(email);
        if (userEntityOpt.isEmpty()) {
            return false;
        }
        return userEntityOpt.get().getPassword().equals(password);
    }

    @Override
    public void login(UserLoginBindModel userLoginBindModel) {
        currentUser.setId(userRepo.findByEmail(userLoginBindModel.getEmail()).get().getId());
        currentUser.setUsername(userLoginBindModel.getEmail());
    }

}
