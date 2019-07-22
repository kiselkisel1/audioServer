package com.example.application.service.impl;

import com.example.application.model.Artist;
import com.example.application.repository.ArtistRepository;
import com.example.application.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    ArtistRepository artistRepository;

    @Override
    public List<Artist> getAll() {
        return artistRepository.findAll();
    }

    @Override
    public Artist getOne(Integer id) {
        return artistRepository.getOne(id);
    }

    @Override
    public Artist add(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public Artist update(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public void delete(Artist artist) {
        artistRepository.delete(artist);
    }
}
