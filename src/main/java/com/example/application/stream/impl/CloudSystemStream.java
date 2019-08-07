package com.example.application.stream.impl;

import com.example.application.model.Song;
import com.example.application.stream.Stream;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CloudSystemStream implements Stream {

    @Override
    public byte[] getBytes(Song song, int startPosition) throws IOException {
        return new byte[0];
    }
}
