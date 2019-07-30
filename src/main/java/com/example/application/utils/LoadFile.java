package com.example.application.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class LoadFile {

    public static String fileLoading(MultipartFile file) throws IOException {

        String uploadPath = "C:\\Users\\Maryia_Kisel1\\Downloads\\audioServer-master\\audioServer\\src\\main\\resources\\audio";
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String resultFileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "\\" + resultFileName));
        return uploadPath + "\\" + resultFileName;
    }

}
