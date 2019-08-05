package com.example.application.controller;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Song;
import com.example.application.service.SongService;
import com.example.application.utils.LoadFile;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
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

        int SIZE_8 = 8192;

        Song song = songService.getOne(id);
        File file = new File(song.getPath());
        String strRange = request.getHeader("Range");

        int range = 0;
        if (strRange != null) {
            range = Integer.parseInt(strRange.replaceAll("\\D+", ""));


            byte[] bytes = Files.readAllBytes(file.toPath());

            byte[] chunk;


            response.reset();

            response.setContentType("audio/mpeg");
            response.addHeader("Content-Transfer-Encoding", "binary");

            if (range + SIZE_8 >= bytes.length) {
                logger.debug("bigger then length of array");
                chunk = Arrays.copyOfRange(bytes, range, bytes.length);
                response.addHeader("Content-Range", "bytes " + range + "-" + (bytes.length - 1) + "/" + file.length());
                logger.debug("from: " + range + " to " + bytes.length);
                response.setStatus(200);
            } else {

                chunk = Arrays.copyOfRange(bytes, range, range + SIZE_8);
                response.addHeader("Content-Range", "bytes " + range + "-" + ((range + SIZE_8) - 1) + "/" + file.length());
                logger.debug("from: " + range + " to " + ((range + SIZE_8) - 1));
                response.setStatus(206);
            }

            response.addHeader("Accept-Range", "bytes");
            response.addHeader("Content-Disposition", "inline");
            response.addHeader("Content-Length", String.valueOf(chunk.length));
            response.addHeader("Connection", "close");


            InputStream byteInputStream = new ByteArrayInputStream(chunk);


            IOUtils.copy(byteInputStream, response.getOutputStream());
            response.flushBuffer();

        } else {
            byte[] bytes = Files.readAllBytes(file.toPath());
            response.reset();
            InputStream byteInputStream = new ByteArrayInputStream(bytes);
            response.setContentType("audio/mpeg");
            response.addHeader("Range", "byte= 0-" + (bytes.length - 1));
            response.addHeader("Content-Transfer-Encoding", "binary");
            response.addHeader("Content-Range", "byte= 0-" + (bytes.length - 1) + "/" + (bytes.length));
            response.addHeader("Accept-Range", "bytes");
            response.addHeader("Content-Disposition", "inline");
            response.addHeader("Content-Length", String.valueOf(bytes.length));
            response.addHeader("Connection", "close");
            IOUtils.copy(byteInputStream, response.getOutputStream());
            response.flushBuffer();
        }
    }


    @PostMapping
    public Song create(@RequestBody @Valid MultipartFile file) throws IOException {

        if (file != null) {
            String fileLocation = LoadFile.fileLoading(file);
            return songService.save(fileLocation);
        } else
            throw new ResourceNotFoundException("THERE_IS_NOT_FILE");

    }

    @DeleteMapping
    public void delete(@RequestParam("id") Integer[] id) {

        for (Integer songId : id) {
            Song song = songService.getOne(songId);
            songService.delete(song);
        }

    }
}
