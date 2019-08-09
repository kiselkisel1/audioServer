package com.example.application.file;

import com.example.application.model.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface SongFile {

    File getFile(String path);
    File saveFile(MultipartFile file);
    void delete(String path);

}
