package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService, UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String homePageView(Model model, Authentication authentication) {
        // logged in user's info
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();

        // get data for logged in user
        List<File> files = fileService.getFilesForCurrentUser(userId);
        List<Note> notes = noteService.getNotesForCurrentUser(userId);
        List<Credential> credentials = credentialService.getCredentialsForCurrentUser(userId);
        // fetch user data
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", credentials);
        return "home";
    }
}
