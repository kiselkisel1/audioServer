package com.example.application.deserializer;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.service.GenreService;
import com.example.application.utils.ValidateCurrentYear;
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


        String name = null;
        Integer start_activity_year=null;
        String notes = null;
        Integer end_activity_year =null;
        Genre genre = null;

        if (node.hasNonNull("name")) {
            name = node.get("name").asText();
        }
        if (node.hasNonNull("notes")) {
            notes = node.get("notes").asText();
        }
        if (node.hasNonNull("end_activity_year")) {
            end_activity_year = ValidateCurrentYear.ValidateYear(node.get("end_activity_year").asInt());
        }

        if (node.hasNonNull("genre")) {
            genre = genreService.getOne(node.get("genre").asInt());
        }
        if (node.hasNonNull("end_activity_year")) {
            start_activity_year = ValidateCurrentYear.ValidateYear(node.get("start_activity_year").asInt());
        }
        return new Artist(name, notes, start_activity_year, end_activity_year, genre);
    }
}
