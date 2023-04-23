package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping()
    public String addOrUpdateCredential(@ModelAttribute("Credential") Credential credential,
                                        Authentication authentication,
                                        RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        String credentialError;
        int requestResponseNumber;

        // new credential
        if (credential.getCredentialId() == null) {
            credential.setUserId(userId);
            credentialService.encryptPassword(credential);
            requestResponseNumber = credentialService.createCredential(credential);
            if (requestResponseNumber < 0) {
                credentialError = "There was an error creating the credential.";
                redirectAttributes.addFlashAttribute("credentialError", credentialError);
            } else {
                redirectAttributes.addFlashAttribute("credentialSuccess", "Successfully created credential.");
            }
        } else {
            // extra configuration for the user's url key & password from DB
            // updates the existing key
            credentialService.updateCredentialWithKey(credential);
            // updates the existing password
            credentialService.encryptPassword(credential);

            // lastly, update existing entire credential
            requestResponseNumber = credentialService.updateCredential(credential);
            if (requestResponseNumber < 0) {
                credentialError = "There was an error updating the credential.";
                redirectAttributes.addFlashAttribute("credentialError", credentialError);
            } else {
                redirectAttributes.addFlashAttribute("credentialSuccess", "Successfully edited credential.");
            }
        }

        return "redirect:/home";
    }


    @GetMapping("/delete/{credentialId}")
    public String deleteCredential (@ModelAttribute("Credential") Credential credential,
                                    RedirectAttributes redirectAttributes){
        int requestResponse = credentialService.deleteCredential(credential);

        if (requestResponse < 0) {
            redirectAttributes.addFlashAttribute("credentialError", "Error deleting credential.");
        } else {
            redirectAttributes.addFlashAttribute("credentialSuccess", "Deleting credential successful.");
        }

        return "redirect:/home";
    }
}

