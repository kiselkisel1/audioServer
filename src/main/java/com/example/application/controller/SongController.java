package com.example.application.controller;

import com.example.application.model.Song;
import com.example.application.service.SongService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("songs")
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAll();
    }

    @GetMapping("{id}")
    public Song getSongById(@PathVariable Integer id) {
        return songService.getOne(id);
    }

    @PostMapping
    public Song create(@RequestBody @Valid Song song) {
        return songService.save(song);
    }

    @PutMapping("{id}")
    public Song update(@PathVariable("id") Integer id,
                       @RequestBody @Valid Song songFromUser) {

        Song song = songService.getOne(id);
        BeanUtils.copyProperties(songFromUser, song, "id");
        return songService.save(song);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        Song song = songService.getOne(id);
        songService.delete(song);
    }
}
