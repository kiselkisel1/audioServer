package com.example.application.service;

import com.example.application.model.Song;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SongService {
    List<Song> getAll();
    Song getOne(Integer id);
    Song save(String fileLocation) ;
     void delete(Song song);
    List<Song> filter(String name, int year, Integer[] artist, Integer[]genres);

    void getStream(Song song, HttpServletResponse response) throws IOException;
    Song findByNameAndYear(String name,Integer year);

}
