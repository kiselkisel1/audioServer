package com.example.application.stream.impl;

import com.example.application.model.Song;
import com.example.application.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@Component
public class FileSystemStream implements Stream {

    @Value("${variable.SIZE_8}")
    private Integer SIZE_8;

    @Override
    public byte[] getBytes(Song song, int startPosition) throws IOException {

        File file = new File(song.getPath());

        byte[] bytes = Files.readAllBytes(file.toPath());

        byte[] chunk;

        if (startPosition + SIZE_8 >= bytes.length) {
            chunk = Arrays.copyOfRange(bytes, startPosition, bytes.length);
        } else {
            chunk = Arrays.copyOfRange(bytes, startPosition, startPosition + SIZE_8);
        }

        return chunk;
    }
}
