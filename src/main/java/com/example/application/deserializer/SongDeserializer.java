package com.example.application.deserializer;

import com.example.application.model.Album;
import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.model.Song;
import com.example.application.service.AlbumService;
import com.example.application.service.GenreService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class SongDeserializer extends StdDeserializer<Song> {

    @Autowired
    AlbumService albumService;

    public SongDeserializer(Class<?> vc) {
        super(vc);
    }
    protected SongDeserializer() {
        this(null);
    }

    @Override
    public Song deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String name = node.get("name").asText();
        int year =  node.get("year").asInt();
        String comment = node.get("comment").asText();

        Album album=null;
        if(node.hasNonNull("genre")){
            album=albumService.getOne(node.get("album").asInt());
        }
        return new Song(name,year,comment, album);
    }
}
