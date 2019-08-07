package com.example.application.stream.factory.impl;

import com.example.application.stream.Stream;
import com.example.application.stream.factory.StreamFactory;
import com.example.application.stream.impl.FileSystemStream;
import org.springframework.stereotype.Component;


@Component
public class FileSystemFactory implements StreamFactory {

    @Override
    public Stream getStream() {
        return new FileSystemStream();
    }
}
