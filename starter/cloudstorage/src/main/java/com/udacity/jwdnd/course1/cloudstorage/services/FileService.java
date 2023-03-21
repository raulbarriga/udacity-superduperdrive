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

    public boolean isDuplicatenOrNoFileUploaded(String fileName) {
        if (fileName.isEmpty())
            return true;

        // check for duplicate file names
        File file = this.getFile(fileName);
        return file != null;
    }

    private File getFile(String fileName) {
        return this.fileMapper.getFile(fileName);
    }
}
