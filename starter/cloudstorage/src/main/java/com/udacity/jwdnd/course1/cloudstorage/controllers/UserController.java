package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @@GetMapping("/signup")
    public String showSignupForm() {
        // the returned string "signup" has to match the thymeleaf template file name
        return "signup";
    }

    @PostMapping("/signup")
    public String registerNewUser(@ModelAttribute("User") User user, Model model) {
        // logic to process the signup form
        String signupError = null;

        // Check if username is available
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "Username already exists";
            model.addAttribute("signupError", signupError);
            return "signup";
        } else {
            // Create user account
            int isUserCreated = userService.createUser(user);
            if (isUserCreated < 0) {
                signupError = "There was an error signing you up. Please try again.";
                model.addAttribute("signupError", signupError);
            } else {
                model.addAttribute("signupSuccess", true);
            }
        }

        return "signup";
    }

    @RequestMapping("/login")
    public String showLoginForm() {
        // logic to show the login form
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute("User") User user) {
        // logic to process the login form
        // TODO: handle the `signupError` & `logoutSuccess` variables from the login.html template
        //
    }
}
