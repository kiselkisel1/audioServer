package com.example.application.controller;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Artist;
import com.example.application.model.Song;
import com.example.application.service.SongService;
import com.example.application.utils.LoadFile;
import org.apache.commons.compress.utils.IOUtils;
import org.farng.mp3.MP3File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.sun.org.apache.xerces.internal.utils.SecuritySupport.getResourceAsStream;

@RestController
@RequestMapping("songs")
public class SongController {

    private static final Logger logger = LoggerFactory.getLogger(Song.class);

    @Autowired
    SongService songService;

    @GetMapping
    public List<Song> getAllSongs(@RequestParam(required=false) String name,
                                  @RequestParam(required=false) Integer year,
                                  @RequestParam(required=false) Integer[] artist,
                                  @RequestParam(required=false) Integer[] genre) {
        if(name!=null && year!=null && artist!=null && genre!=null){
            return songService.filter(name,year,artist,genre);
        }
        return songService.getAll();
    }



    @GetMapping("{id}")
    public void getMp3(@PathVariable Integer id,HttpServletResponse response) throws IOException {

        songService.getStream(songService.getOne(id),response);
     }



    @PostMapping
    public Song create(@RequestBody @Valid MultipartFile file) throws IOException {

         if(file!=null){
             String fileLocation = LoadFile.fileLoading(file);
             return songService.save(fileLocation);
        }
         else
             throw new ResourceNotFoundException("THERE_IS_NOT_FILE");

      }

    @DeleteMapping
    public void delete(@RequestParam("id") Integer[] id) {

        for(Integer songId:id){
            Song song = songService.getOne(songId);
            songService.delete(song);
        }

    }
}
