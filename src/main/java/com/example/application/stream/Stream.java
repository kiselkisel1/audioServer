package com.example.application.stream;

import com.example.application.model.Song;

import java.io.IOException;

public interface Stream {

    byte[] getBytes(Song song,int startPosition) throws IOException;
}
