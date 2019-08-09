package com.example.application.stream.impl;

import com.example.application.model.Song;
import com.example.application.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.RandomAccessFile;


//@PropertySource("classpath:path.properties")
@Component
public class FileSystemStream implements Stream {

    private static final Logger logger = LoggerFactory.getLogger(Song.class);

    private RandomAccessFile file;

//    @Value("${variable.SIZE_512:1048576}")
//    private Integer chunk;
//
//    @Autowired
//    Environment environment;
//
//    private String uploadPath;
//
//    @PostConstruct
//    public void init( ) {
//        logger.debug("it is work");
//         uploadPath=environment.getProperty("file.uploadPath");
//         if(uploadPath==null){
//             uploadPath="C:\\Users\\Maryia_Kisel1\\Downloads\\";
//         }
//    }


    @Value("${file.uploadPath: C:\\Users\\Maryia_Kisel1\\Downloads}")
    private String uploadPath;


    @Override
    public byte[] getBytes(Song song, int startPosition) throws IOException {

Integer chunk=1024;
         // открываем файл для чтения
        file = new RandomAccessFile(uploadPath+song.getPath(), "r");

        byte[] buffer = new byte[chunk];


        try {
            // ставим указатель на нужный вам символ
            file.seek(startPosition);

            if (startPosition + chunk >= song.getSize()) {

                int length = Math.toIntExact(song.getSize()) - startPosition;
                buffer = new byte[length];
                file.read(buffer, 0, length);

            } else {
                file.read(buffer, 0, chunk);
            }
        } finally {
            file.close();
        }

        return buffer;

    }
}


//    @Override
//    public byte[] getBytes(Song song, int startPosition) throws IOException {
//
//
//        File file = new File(song.getPath());
//
//        byte[] bytes = Files.readAllBytes(file.toPath());
//
//        byte[] chunk;
//
//        if (startPosition + SIZE_1 >= bytes.length) {
//            chunk = Arrays.copyOfRange(bytes, startPosition, bytes.length);
//
//            logger.debug("start position "+startPosition+" array length "+bytes.length);
//
//        } else {
//            chunk = Arrays.copyOfRange(bytes, startPosition, startPosition + SIZE_1);
//            logger.debug("size 8"+SIZE_1);
//        }
//
//        return chunk;
//    }