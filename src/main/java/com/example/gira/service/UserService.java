package com.example.gira.service;

import com.example.gira.model.binding.UserLoginBindModel;
import com.example.gira.model.service.UserServiceModel;

public interface UserService {

    boolean checkExistsByUsername(String username);

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    boolean authenticate(String email, String password);

    void login(UserLoginBindModel userLoginBindModel);
}
