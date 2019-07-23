package com.example.application.service;

import com.example.application.model.Artist;
import com.example.application.model.Song;

import java.util.List;

public interface SongService {
    List<Song> getAll();
    Song getOne(Integer id);
    Song save(Song song);
    void delete(Song song);
}
