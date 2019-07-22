package com.example.application.controller;


import com.example.application.model.Genre;
import com.example.application.repository.GenreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
public class GenreController {

    @Autowired
    GenreRepository genreRepository;

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("{id}")
    public Genre getOne(@PathVariable("id") Genre genre){
        return genre;
    }

    @PostMapping
    public Genre create(@RequestBody Genre genre){

        return genreRepository.save(genre);
    }

    @PutMapping("{id}")
    public Genre update(@PathVariable("id") Genre genreFromDb,
                        //spring из тела запроса(json) сам разбирает данные и кладет их в обьект типа artist
                        @RequestBody Genre genreFromUser ){
        BeanUtils.copyProperties(genreFromUser,genreFromDb,"id");
        return genreRepository.save(genreFromDb);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Genre genre) {
        genreRepository.delete(genre);
    }
}
