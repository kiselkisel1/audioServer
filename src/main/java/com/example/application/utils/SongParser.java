package com.example.application.utils;

import com.example.application.exceptions.ResourceNotFoundException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class SongParser {

    private static final String uploadPath = "C:\\Users\\Maryia_Kisel1\\Downloads\\audioServer-master\\audioServer\\src\\main\\resources\\audio";

    public static File getFileAfterLoading(MultipartFile file) {
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

    public static Metadata readSong(File file) throws IOException, TikaException, SAXException {

        InputStream input = new FileInputStream(file);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        parser.parse(input, handler, metadata, parseCtx);
        input.close();

        return metadata;
    }
}
