package com.example.application.file.impl;

import com.example.application.file.SongFile;
import com.example.application.model.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class CloudSystemImpl implements SongFile {
    @Override
    public File getFile(String path) {
        return null;
    }

    @Override
    public File saveFile(MultipartFile file) {
        return null;
    }

    @Override
    public void delete(String path) {

    }
}
