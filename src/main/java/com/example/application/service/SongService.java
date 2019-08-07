package com.example.application.service;

import com.example.application.model.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {
    List<Song> getAll();
    Song getOne(Integer id);
     Song save(MultipartFile multipartFile) ;
     void delete(Song song);
    List<Song> filter(String name, int year, Integer[] artist, Integer[]genres);

    byte[] getStream(Song song,int range);
    Song findByNameAndYear(String name,Integer year);

    Integer getStartPosition(String range);

}
