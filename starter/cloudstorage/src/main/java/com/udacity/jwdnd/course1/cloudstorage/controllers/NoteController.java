package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping()
    public String addOrUpdateNote(@ModelAttribute("Note") Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        String noteError;
        int requestResponseNumber;
        //String noteDescription = note.getNoteDescription();
        //if (noteDescription != null && noteDescription.length() > 1000) {
        //    // handle the case where the noteDescription is too long
        //    // for example, you can return an error message to the user
        //    noteError = "The note description can only be less than 1,000 characters long.";
        //    redirectAttributes.addFlashAttribute("noteError", noteError);
        //    return "redirect:/home";
        //}

        // new note
        if(note.getNoteId() == null) {
            note.setUserId(userId);
            requestResponseNumber = noteService.createNote(note);
            if (requestResponseNumber < 0) {
                noteError = "There was an error creating the note.";
                redirectAttributes.addFlashAttribute("noteError", noteError);
            } else {
                redirectAttributes.addFlashAttribute("noteSuccess", "Successfully created note.");
            }
        } else {
            // update existing one
            requestResponseNumber = noteService.updateNote(note);
            if (requestResponseNumber < 0) {
                noteError = "There was an error updating the note.";
                redirectAttributes.addFlashAttribute("noteError", noteError);
            } else {
                redirectAttributes.addFlashAttribute("noteSuccess", "Successfully edited note.");
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@ModelAttribute("Note") Note note, RedirectAttributes redirectAttributes){
        int requestResponse = noteService.deleteNote(note);

        if (requestResponse < 0) {
            redirectAttributes.addFlashAttribute("noteError", "Error deleting note.");
        } else {
            redirectAttributes.addFlashAttribute("noteSuccess", "Deleting note successful.");
        }

        return "redirect:/home";
    }
}
