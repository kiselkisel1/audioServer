package com.example.application.service.impl;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Album;
import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.model.Song;
import com.example.application.repository.AlbumRepository;
import com.example.application.repository.ArtistRepository;
import com.example.application.repository.GenreRepository;
import com.example.application.repository.SongRepository;
import com.example.application.service.SongService;
import com.example.application.utils.SongParser;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Service
@Transactional
public class SongServiceImpl implements SongService {

    private static final Logger logger = LoggerFactory.getLogger(Song.class);

    @Autowired
    SongRepository songRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public List<Song> getAll() {
        return songRepository.findAll();
    }

    @Override
    public Song getOne(Integer id) {
        return songRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("SONG_DOES_NOT_EXIST"));
    }

    @Override
    public Song save(String fileLocation) {

        Metadata metadata;

        Song song = null;
        Song songFromDb = null;

        try {
            metadata = SongParser.readSong(fileLocation);

            songFromDb = songRepository.findByNameAndYear(metadata.get("title"), Integer.parseInt(metadata.get("xmpDM:releaseDate")));
            Album album = albumRepository.findByNameAndYear(metadata.get("xmpDM:album"), 2000);
            Artist artist = artistRepository.findByName(metadata.get("xmpDM:artist"));
            Genre genre = genreRepository.findByName(metadata.get("xmpDM:genre"));

            if (genre == null) {
                genre = new Genre(metadata.get("xmpDM:genre"));
            }
            if (artist == null) {
                artist = new Artist(metadata.get("xmpDM:artist"), "notes", 1900, 2010, genre);

            }
            if (album == null) {
                album = new Album(metadata.get("xmpDM:album"), 2000, null, artist);

            }
            if (song == null) {
                song = new Song(metadata.get("title"), Integer.parseInt(metadata.get("xmpDM:releaseDate")), null, fileLocation, album);

            }

            artist.setGenre(genre);
            album.setArtist(artist);
            song.setAlbum(album);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        if (songFromDb != null) {
            return songFromDb;
        } else {
            return songRepository.save(song);
        }
    }


    @Override
    public void delete(Song song) {

        File file = new File(song.getPath());
        file.delete();
        songRepository.delete(song);

    }

    @Override
    public List<Song> filter(String name, int year, Integer[] artist, Integer[] genres) {
        return songRepository.filter(name, year, artist, genres);
    }

    @Override
    public void getStream(Song song, HttpServletResponse response) throws IOException {

        File file=new File(song.getPath());
        InputStream input = new FileInputStream(file);
        logger.debug("file name "+file.getName());

        // Set the content type and attachment header.
        response.addHeader("Content-disposition", "attachment;filename="+file.getName());
        response.setContentType("audio/mpeg");
        // Copy the stream to the response's output stream.
        IOUtils.copy(input, response.getOutputStream());
        response.flushBuffer();

    }

    @Override
    public Song findByNameAndYear(String name, Integer year) {
        return songRepository.findByNameAndYear(name, year);
    }
}
