package com.example.gira.web;

import com.example.gira.model.binding.UserLoginBindModel;
import com.example.gira.model.binding.UserRegBindModel;
import com.example.gira.model.service.UserServiceModel;
import com.example.gira.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        if (!model.containsAttribute("userRegBindModel")) {
            model.addAttribute("userRegBindModel", new UserRegBindModel())
                    .addAttribute("passwordsNotMatch", false)
                    .addAttribute("usernameExists", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegBindModel userRegBindModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // check for input validations
        if (bindingResult.hasErrors() ||
                !userRegBindModel.getPassword().equals(userRegBindModel.getConfirmPassword()) ||
                userService.checkExistsByUsername(userRegBindModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userRegBindModel", userRegBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegBindModel", bindingResult);
            // check for matching passwords
            if (!userRegBindModel.getPassword().equals(userRegBindModel.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("passwordsNotMatch", true);
            }
            // check for existing Username in DB
            if (userService.checkExistsByUsername(userRegBindModel.getUsername())) {
                redirectAttributes.addFlashAttribute("usernameExists", true);
            }
            return "redirect:/users/register";
        }
        userService.registerUser(modelMapper.map(userRegBindModel, UserServiceModel.class));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        if (!model.containsAttribute("userLoginBindModel")) {
            model.addAttribute("userLoginBindModel", new UserLoginBindModel())
                    .addAttribute("notAuthorized", false);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginBindModel userLoginBindModel,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (!userService.authenticate(userLoginBindModel.getEmail(), userLoginBindModel.getPassword())) {
            redirectAttributes.addFlashAttribute("userLoginBindModel", userLoginBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindModel", bindingResult)
                    .addFlashAttribute("notAuthorized", true);
            return "redirect:/users/login";
        }
        userService.login(userLoginBindModel);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
