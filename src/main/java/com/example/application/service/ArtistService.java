package com.example.application.service;

import com.example.application.model.Artist;

import java.util.List;

public interface ArtistService {

    List<Artist> getAll();
    Artist getOne(Integer id);
    Artist save(Artist artist);
    void delete(Artist artist);

}
