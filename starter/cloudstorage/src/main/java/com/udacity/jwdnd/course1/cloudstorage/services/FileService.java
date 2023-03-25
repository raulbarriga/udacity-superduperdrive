package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesForCurrentUser(Integer userId) {
        return fileMapper.getFilesForUser(userId);
    }

    public int insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    public int deleteFile(File file) {
        return fileMapper.deleteFile(file.getFileId());
    }

    public boolean isDuplicateOrNoFileUploaded(String fileName, Integer userId) {
        if (fileName.isEmpty())
            return true;

        // check for duplicate file names
        File file = this.getFileByName(fileName, userId);
        // returns true if there's a duplicate file name in the db
        // (comparison will not be null since there is a file with provided name)
        return file != null;
    }

    public File getFileByName(String fileName, Integer userId) {
        return this.fileMapper.getFile(fileName, userId);
    }
}
