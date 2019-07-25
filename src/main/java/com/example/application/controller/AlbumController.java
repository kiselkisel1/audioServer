package com.example.application.controller;

import com.example.application.model.Album;
import com.example.application.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    private static final Logger logger = LoggerFactory.getLogger(Album.class);

    @GetMapping
    public List<Album> getAllAlbums(@RequestParam(required=false) String name,
                                    @RequestParam(required=false) Integer year,
                                    @RequestParam(required=false) Integer[] artist,
                                    @RequestParam(required=false) Integer[] genre) {

         if(name!=null && year!=null && artist!=null && genre!=null){
              return albumService.filter(name,year,artist,genre);
        }
        return albumService.getAll();
    }

    @GetMapping("{id}")
    public Album getAlbumById(@PathVariable Integer id) {
        return albumService.getOne(id);
    }

    @PostMapping
    public Album create(@RequestBody @Valid Album album){
        return albumService.save(album);
    }

    @PutMapping("{id}")
    public Album update(@PathVariable("id") Integer id,
                        @RequestBody @Valid Album artistFromUser ){

        Album album=albumService.getOne(id);
        BeanUtils.copyProperties(artistFromUser,album,"id");
        return albumService.save(album);
    }


    @DeleteMapping
    public void delete(@RequestParam("id") Integer[] id) {

        for(Integer albumId:id){
            Album album=albumService.getOne(albumId);
            albumService.delete(album);
        }

    }
}