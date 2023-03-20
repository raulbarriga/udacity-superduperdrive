package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showSignupForm() {
        // the returned string "signup" has to match the thymeleaf template file name
        return "signup";
    }

    @PostMapping()
    public String registerNewUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        String signupError = null;
        boolean signUpSuccessful = false;

        // Check if username is available
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "Username already exists";
            redirectAttributes.addFlashAttribute("signupError", signupError);
        }

        if (signupError == null) {
            // Create user account
            int isUserCreated = userService.createUser(user);
            if (isUserCreated < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            signUpSuccessful = true;
        } else {
            redirectAttributes.addFlashAttribute("signupError", signupError);
        }

        if (signUpSuccessful) {
            return "redirect:/login";
        } else {
            return "signup";
        }
    }
}
