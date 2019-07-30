package com.example.application.service.impl;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Artist;
import com.example.application.repository.ArtistRepository;
import com.example.application.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    private static final Logger logger = LoggerFactory.getLogger(Artist.class);

    @Autowired
    ArtistRepository artistRepository;

    @Override
    public List<Artist> getAll() {
        return artistRepository.findAll();
    }

    @Override
    public Artist getOne(Integer id) {

        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ARTIST_DOES_NOT_EXIST"));
    }

    @Override
    public Artist save(Artist artist) {

        return artistRepository.save(artist);
    }

    @Override
    public void delete(Artist artist) {
        artistRepository.delete(artist);
    }

    @Override
    public List<Artist> filter(String name, int year, Integer[] genreId) {
        return artistRepository.filter(name, year, genreId);
    }

    @Override
    public Artist findByName(String name) {
        return artistRepository.findByName(name);
    }

}
