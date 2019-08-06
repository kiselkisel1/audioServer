package com.example.application.controller;

import com.example.application.model.Song;
import com.example.application.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("songs")
public class SongController {

    private static final Logger logger = LoggerFactory.getLogger(Song.class);

    @Autowired
    SongService songService;


    @GetMapping
    public List<Song> getAllSongs(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) Integer[] artist,
                                  @RequestParam(required = false) Integer[] genre) {
        if (name != null && year != null && artist != null && genre != null) {
            return songService.filter(name, year, artist, genre);
        }
        return songService.getAll();
    }


    @GetMapping("{id}")
    public void getOne(@PathVariable Integer id, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Song song = songService.getOne(id);
        songService.getStream(song, response, request);

    }


    @PostMapping
    public Song create(@RequestBody @Valid MultipartFile file) {

       return songService.save(file);
    }

    @DeleteMapping
    public void delete(@RequestParam("id") Integer[] id) {

        for (Integer songId : id) {
            Song song = songService.getOne(songId);
            songService.delete(song);
        }

    }
}
