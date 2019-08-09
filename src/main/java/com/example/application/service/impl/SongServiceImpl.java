package com.example.application.service.impl;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.file.SongFile;
import com.example.application.file.impl.FileSystemImpl;
import com.example.application.model.*;
import com.example.application.repository.*;
import com.example.application.service.SongService;
import com.example.application.stream.Stream;
import com.example.application.stream.StreamFactory;
import com.example.application.stream.impl.FileSystemStream;
import com.example.application.utils.SongParser;
import com.example.application.utils.StorageType;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import sun.security.smartcardio.SunPCSC;

import java.io.File;
import java.io.IOException;
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
    StorageRepository storageRepository;

   @Autowired
   StreamFactory streamFactory;



    @Autowired
    FileSystemImpl fileSystem;

    private Storage getStorage() {
       Storage storage=storageRepository.findByType(StorageType.FILE_SYSTEM);
        if(storage==null){
            return new Storage(StorageType.FILE_SYSTEM);
        }
        return storage;
    }

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
            File file = fileSystem.saveFile(multipartFile);
            metadata = SongParser.parseSong(file);


            Integer songYear = null;
            try {
                songYear = Integer.parseInt(metadata.get("xmpDM:releaseDate"));
            } catch (NumberFormatException e) {
             logger.warn("Year is not found.Set default value null");
            }

            songFromDb = songRepository.findByName(metadata.get("title"));
            Album album = albumRepository.findByName(metadata.get("xmpDM:album"));
            Artist artist = artistRepository.findByName(metadata.get("xmpDM:artist"));
            Genre genre = genreRepository.findByName(metadata.get("xmpDM:genre"));


            if (genre == null && metadata.get("xmpDM:genre")!=null ) {
                genre = new Genre(metadata.get("xmpDM:genre"));
            }

            if (artist == null) {
                artist = new Artist(metadata.get("xmpDM:artist"), genre);

            }
            if (album == null) {
                album = new Album(metadata.get("xmpDM:album"), artist);

            }
            if (song == null) {
                song = new Song(metadata.get("title"), songYear, getStorage(), file.length(), file.getName(), album);

            }

            artist.setGenre(genre);
            album.setArtist(artist);
            song.setAlbum(album);
            song.setStorage(getStorage());

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

        fileSystem.delete(song.getPath());
        songRepository.delete(song);

    }

    @Override
    public List<Song> filter(String name, int year, Integer[] artist, Integer[] genres) {
        return songRepository.filter(name, year, artist, genres);
    }

    @Override
    public byte[] getStream(Song song,  int startPosition) {

//        StreamFactory streamFactory=new StreamFactory();
         Stream stream=streamFactory.getStream(song.getStorage().getType());


        byte[] chunk = null;
        try {
            chunk = stream.getBytes(song, startPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chunk;

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
        return range;
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

