package com.example.application.controller;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Artist;
import com.example.application.repository.ArtistRepository;
import com.example.application.service.ArtistService;
import com.example.application.validator.ArtistValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("artists")
public class MainController {

    @Autowired
   ArtistRepository artistRepository;

    @Autowired
    ArtistValidator artistValidator;

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
}

//    @GetMapping("{id}")
//    public Artist getOne(@PathVariable("id") Artist artist){
//        return artist;
//    }

    @GetMapping("{id}")
    public ResponseEntity<Artist> getUsersById(@PathVariable Long id)
            throws ResourceNotFoundException {
       Artist artist=artistRepository.findById(id)
                         .orElseThrow(() -> new ResourceNotFoundException("ARTIST_DOES_NOT_EXIST"));
        return ResponseEntity.ok().body(artist);
    }


//    @PostMapping
//    public ResponseEntity<Artist> create(@RequestBody  @Valid Artist artist, BindingResult result){
//        artistValidator.validate(artist,result);
//        if(result.hasErrors()){
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//
//        }
//       Artist newArtist= artistRepository.save(artist);
//        return ResponseEntity.ok().body(newArtist);
//    }

    @PostMapping
    public Artist  create(@RequestBody  Artist artist){

        return artistRepository.save(artist);
    }

    @PutMapping("{id}")
    public Artist update(@PathVariable("id") Artist artistFromDb,
                         //spring из тела запроса(json) сам разбирает данные и кладет их в обьект типа artist
                         @RequestBody Artist artistFromUser ){
        BeanUtils.copyProperties(artistFromUser,artistFromDb,"id");
        return artistRepository.save(artistFromDb);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Artist artist) {
        artistRepository.delete(artist);
    }
    }


