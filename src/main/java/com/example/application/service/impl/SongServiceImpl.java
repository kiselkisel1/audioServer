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
import com.example.application.stream.Stream;
import com.example.application.stream.impl.FileSystemStream;
import com.example.application.utils.SongParser;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
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

    @Autowired
    FileSystemStream fileSystemStream;


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
    public Song save(MultipartFile multipartFile) {

        Metadata metadata;

        Song song = null;
        Song songFromDb = null;

        try {
            File file = SongParser.getFileAfterLoading(multipartFile);
            metadata = SongParser.readSong(file);


            Integer songYear = null;
            try {
                songYear = Integer.parseInt(metadata.get("xmpDM:releaseDate"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            songFromDb = songRepository.findByNameAndYear(metadata.get("title"), songYear);
            Album album = albumRepository.findByName(metadata.get("xmpDM:album"));
            Artist artist = artistRepository.findByName(metadata.get("xmpDM:artist"));
            Genre genre = genreRepository.findByName(metadata.get("xmpDM:genre"));

            if (genre == null) {
                genre = new Genre(metadata.get("xmpDM:genre"));
            }
            if (artist == null) {
                artist = new Artist(metadata.get("xmpDM:artist"), genre);

            }
            if (album == null) {
                album = new Album(metadata.get("xmpDM:album"), artist);

            }
            if (song == null) {
                song = new Song(metadata.get("title"), songYear, file.getAbsolutePath(),file.length(), album);

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
    public byte[] getStream(Song song, int range) {

        byte[] chunk=null;
        try {
            chunk = fileSystemStream.getBytes(song, range);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chunk;

    }

    @Override
    public Song findByNameAndYear(String name, Integer year) {
        return songRepository.findByNameAndYear(name, year);
    }

    @Override
    public Integer getStartPosition(String strRange) {

        Integer range = 0;

        if (strRange != null) {
            try {
            range = Integer.parseInt(strRange.replaceAll("\\D+", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return  range;
    }
}


//        File file = new File(song.getPath());
//        String strRange = request.getHeader("Range");
//
//        int range = 0;
//        if (strRange != null) {
//            range = Integer.parseInt(strRange.replaceAll("\\D+", ""));
//        }
//
//        byte[] bytes = Files.readAllBytes(file.toPath());
//
//        byte[] chunk;
//
//        response.reset();
//
//        response.setContentType("audio/mpeg");
//        response.addHeader("Content-Transfer-Encoding", "binary");
//
//        if (range + SIZE_8 >= bytes.length) {
//
//            chunk = Arrays.copyOfRange(bytes, range, bytes.length);
//            response.addHeader("Content-Range", "bytes " + range + "-" + (bytes.length - 1) + "/" + file.length());
//            logger.debug("from: " + range + " to " + bytes.length);
//            response.setStatus(200);
//        } else {
//            chunk = Arrays.copyOfRange(bytes, range, range + SIZE_8);
//            response.addHeader("Content-Range", "bytes " + range + "-" + ((range + SIZE_8) - 1) + "/" + file.length());
//            logger.debug("from: " + range + " to " + ((range + SIZE_8) - 1));
//            response.setStatus(206);
//        }
//
//        response.addHeader("Accept-Range", "bytes");
//        response.addHeader("Content-Disposition", "inline");
//        response.addHeader("Content-Length", String.valueOf(chunk.length));
//        response.addHeader("Connection", "close");
//
//        InputStream byteInputStream = new ByteArrayInputStream(chunk);
//        IOUtils.copy(byteInputStream, response.getOutputStream());
//        response.flushBuffer();

