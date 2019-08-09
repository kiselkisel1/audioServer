package com.example.application.stream;

import com.example.application.stream.impl.CloudSystemStream;
import com.example.application.stream.impl.FileSystemStream;
import com.example.application.utils.StorageType;
import org.springframework.stereotype.Component;

@Component
public class StreamFactory {

//    @Autowired
//     CloudSystemStream cloudSystemStream;
//
//    @Autowired
//    FileSystemStream fileSystemStream;

    public Stream getStream(StorageType type) {

        Stream stream = null;
        switch (type) {
            case CLOUD_SYSTEM:
                stream = new CloudSystemStream();
                break;

            case FILE_SYSTEM:
                stream = new FileSystemStream();
                break;
            default:
                throw new IllegalArgumentException("Wrong storage type:" + type);
        }
        return stream;
    }
}
