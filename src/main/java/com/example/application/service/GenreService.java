package com.example.application.service;

 import com.example.application.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();
    Genre getOne(Integer id);
    Genre save(Genre genre);
    void delete(Genre genre);
}
