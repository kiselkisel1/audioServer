package com.example.application.service;

import com.example.application.model.Album;

import java.util.List;

public interface AlbumService {
    List<Album> getAll();
    Album getOne(Integer id);
    Album save(Album album);
    void delete(Album album);

}
