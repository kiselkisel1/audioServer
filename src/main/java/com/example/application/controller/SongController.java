package com.example.application.controller;

import com.example.application.model.Song;
import com.example.application.service.SongService;
import com.example.application.utils.MultipartFileSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


//    @GetMapping("{id}")
//    public void getOne(@PathVariable Integer id,HttpServletResponse response) throws IOException {
//
//        songService.getStream(songService.getOne(id),response);
//
//     }


    @GetMapping("{id}")
    public ResponseEntity<byte[]> getsong(@PathVariable Integer id, HttpServletResponse response) {
        try {
            Song song=songService.getOne(id);
            Path path = Paths.get(song.getPath());


            response.setContentType("audio/mpeg");
            response.addHeader("Content-disposition", "attachment;filename="+path.getFileName());

            response.setContentLength((int) Files.size(path));
            Files.copy(path, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception ignored) {
        }
        return null;
    }

//    @GetMapping("{id}")
//    public void getEpisodeFile(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Song song = songService.getOne(id);
//
//        MultipartFileSender.fromURIString(song.getPath())
//                .with(request)
//                .with(response)
//                .serveResource();
//
//    }




//    @PostMapping
//    public Song create(@RequestBody @Valid MultipartFile file) throws IOException {
//
//         if(file!=null){
//             String fileLocation = LoadFile.fileLoading(file);
//             return songService.save(fileLocation);
//        }
//         else
//             throw new ResourceNotFoundException("THERE_IS_NOT_FILE");
//
//      }

    @DeleteMapping
    public void delete(@RequestParam("id") Integer[] id) {

        for (Integer songId : id) {
            Song song = songService.getOne(songId);
            songService.delete(song);
        }

    }
}
