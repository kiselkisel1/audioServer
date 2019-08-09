package com.example.application.file.impl;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.file.SongFile;
import com.example.application.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@PropertySource("classpath:path.properties")
@Component
public class FileSystemImpl implements SongFile {

    @Value("${file.uploadPath: C:\\Users\\Maryia_Kisel1\\Downloads}")
    private String uploadPath;

    @Override
    public File getFile(String path) {
        return new File(uploadPath+path);
    }

    @Override
    public File saveFile(MultipartFile file) {

        if (file != null) {

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filePath = uploadPath + "\\" + UUID.randomUUID().toString() + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new File(filePath);

        } else
            throw new ResourceNotFoundException("THERE_IS_NOT_FILE");
    }

    @Override
    public void delete(String path) {
        File file = new File(uploadPath+path);
        file.delete();
    }
}
