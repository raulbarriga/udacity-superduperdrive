package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute("File") MultipartFile multipartFile, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        String fileUploadError = null;
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long fileSize = multipartFile.getSize();
        long oneMB = 1024 * 1024;

        // check for errors
        if (fileService.isDuplicatenOrNoFileUploaded(fileName)) {
            if (fileName.isEmpty()) {
                fileUploadError = "Please 'choose a file' before attempting to upload. ";
                redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);
            }
            else {
                fileUploadError = "Filename: " + fileName + " is already taken. Try a different name. ";
                redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);
            }
        } else if (fileSize > oneMB) {
            fileUploadError = "Fil size is greater than the limit of 1MB";
        } else {
            // handle successful file upload
            int isFileInserted = fileService.insertFile(new File(
                    null,
                    fileName,
                    multipartFile.getContentType(),
                    String.valueOf(fileSize),
                    userId,
                    multipartFile.getBytes()
            ));

            if (isFileInserted < 0) {
                fileUploadError = "There was an error uploading file.";
            }
        }

        if (fileUploadError == null) {
            redirectAttributes.addFlashAttribute("fileUploadSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("fileUploadError", fileUploadError);
        }

        return "redirect:/home";
    }
    
    @GetMapping("/delete/{fileId}")
    public String deleteFile(@ModelAttribute("File") File file, RedirectAttributes redirectAttributes){
        int requestResponse = fileService.deleteFile(file);

        if (requestResponse < 0) {
            redirectAttributes.addFlashAttribute("fileError", "Error deleting file.");
        } else {
            redirectAttributes.addFlashAttribute("fileSuccess", "Deleting file successful.");
        }

        return "redirect:/home";
    }
}
