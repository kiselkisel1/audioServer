package com.example.application.service.impl;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Album;
import com.example.application.repository.AlbumRepository;
import com.example.application.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    private static final Logger logger = LoggerFactory.getLogger(Album.class);


    @Override
    public List<Album> getAll() {
        return albumRepository.findAll();
    }

    @Override
    public Album getOne(Integer id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ALBUM_DOES_NOT_EXIST"));
    }

    @Override
    public Album save(Album album) {

        return albumRepository.save(album);
    }

    @Override
    public void delete(Album album) {
        albumRepository.delete(album);
    }

    @Override
    public List<Album> filter(String name, int year, Integer[] artists, Integer[] genres) {
        logger.debug("it works");
        return albumRepository.filter(name, year, artists, genres);
    }

    @Override
    public Album findByName (String name ) {
        return albumRepository.findByName (name );
    }

}
