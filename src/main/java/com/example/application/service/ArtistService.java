package com.example.application.service;

import com.example.application.model.Artist;

import java.util.List;

public interface ArtistService {

    List<Artist> getAll();
    Artist getOne(Long id);
    Artist add(Artist artist);
    Artist update(Artist artist);
    void delete(Artist artist);
}
