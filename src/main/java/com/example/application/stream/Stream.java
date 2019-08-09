package com.example.application.stream;

import com.example.application.model.Song;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;

 public interface Stream {

    byte[] getBytes(Song song,int startPosition) throws IOException;
}
