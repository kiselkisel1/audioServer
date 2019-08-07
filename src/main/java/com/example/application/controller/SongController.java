package com.example.application.controller;

import com.example.application.model.Song;
import com.example.application.service.SongService;
import com.example.application.stream.Stream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.security.acl.LastOwnerException;
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

        Integer startPosition = songService.getStartPosition(request.getHeader("Range"));

        byte[] chunk = songService.getStream(song, startPosition);

        response.reset();
        response.setContentType("audio/mpeg");
        response.addHeader("Content-Transfer-Encoding", "binary");
        if (startPosition + chunk.length >= song.getFile_length()) {
            response.setStatus(200);

        } else {
            response.setStatus(206);
        }

        response.addHeader("Content-Range", "bytes " + startPosition + "-" + (startPosition + chunk.length - 1) + "/" + song.getFile_length());
        logger.debug("from: " + startPosition + " to " + (startPosition + chunk.length - 1));
        response.addHeader("Accept-Range", "bytes");
        response.addHeader("Content-Disposition", "inline");
        response.addHeader("Content-Length", String.valueOf(chunk.length));
        response.addHeader("Connection", "close");

        InputStream byteInputStream = new ByteArrayInputStream(chunk);
        IOUtils.copy(byteInputStream, response.getOutputStream());

        response.flushBuffer();

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
