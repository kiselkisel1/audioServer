package com.example.application.service.impl;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Song;
import com.example.application.repository.SongRepository;
import com.example.application.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository songRepository;

    @Override
    public List<Song> getAll() {
        return songRepository.findAll();
    }

    @Override
    public Song getOne(Integer id) {
        return songRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("SONG_DOES_NOT_EXIST"));
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void delete(Song song) {
    songRepository.delete(song);
    }

    @Override
    public List<Song> filter(String name, int year, Integer[] artist, Integer[] genres) {
        return songRepository.filter(name,year,artist,genres);
    }
}
