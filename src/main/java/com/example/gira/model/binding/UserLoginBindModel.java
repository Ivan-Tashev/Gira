package com.example.gira.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginBindModel {
    @NotNull @Email(message = "Email cant be empty!")
    private String email;
    @NotNull @Size(min = 3, max = 20, message = "Password must be between 3 and 20 symbols.")
    private String password;

    public String getEmail() {
        return email;
    }

    public UserLoginBindModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginBindModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
