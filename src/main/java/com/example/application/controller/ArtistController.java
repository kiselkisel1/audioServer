package com.example.application.controller;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Artist;
import com.example.application.service.ArtistService;
import com.example.application.validator.ArtistValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("artists")
public class ArtistController {

    private static final Logger logger = LoggerFactory.getLogger(Artist.class);

    @Autowired
    ArtistService artistService;

    @GetMapping
    public List<Artist> getAllArtists(@RequestParam(required=false) String name,
                                      @RequestParam(required=false) Integer year,
                                      @RequestParam(required=false) Integer[] genre) {

        logger.debug("name"+name+" year "+year+"genreId "+genre);
        if(name!=null && year!=null && genre!=null ){
            logger.debug("inside filter");
            return artistService.filter(name,year,genre);
        }

         return artistService.getAll();
    }

    @GetMapping("{id}")
    public Artist getArtistById(@PathVariable Integer id) {
        Artist artist=artistService.getOne(id);

        return artist;
    }

    @PostMapping
    public Artist create(@Valid @RequestBody Artist artist){

        return artistService.save(artist);
    }

    @PutMapping("{id}")
    public Artist update(@PathVariable("id") Integer id,
                         @RequestBody @Valid Artist artistFromUser ){
        logger.debug("id "+id+" = artistFromUser "+artistFromUser.toString() );

        Artist artist=artistService.getOne(id);
        BeanUtils.copyProperties(artistFromUser,artist,"id");
        return artistService.save(artist);
    }

    @DeleteMapping
    public void delete(@RequestParam("id") Integer[] id) {

        for(Integer artistId:id){
            Artist artist=artistService.getOne(artistId);
            artistService.delete(artist);
        }

    }

}


