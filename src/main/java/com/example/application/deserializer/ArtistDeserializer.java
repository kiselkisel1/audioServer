package com.example.application.deserializer;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
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

public class ArtistDeserializer extends StdDeserializer<Artist> {

    private static final Logger logger = LoggerFactory.getLogger(Artist.class);

    @Autowired
    GenreService genreService;

    protected ArtistDeserializer(Class<?> vc) {
        super(vc);
    }
    protected ArtistDeserializer() {
        this(null);
    }

    @Override
    public Artist deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);


    String name = node.get("name").asText();
    String notes = node.get("notes").asText();
    int start_activity_year =  node.get("start_activity_year").asInt();
    int end_activity_year = node.get("end_activity_year").asInt();

    Genre genre=genreService.getOne(node.get("genre").asInt());

    return new Artist(name,notes,start_activity_year,end_activity_year,genre);
}
}
