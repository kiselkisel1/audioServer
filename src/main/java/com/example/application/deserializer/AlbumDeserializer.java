package com.example.application.deserializer;

import com.example.application.model.Album;
import com.example.application.model.Artist;
import com.example.application.service.ArtistService;
import com.example.application.utils.ValidateCurrentYear;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AlbumDeserializer extends StdDeserializer<Album> {

    private static final Logger logger = LoggerFactory.getLogger(Album.class);

    @Autowired
    ArtistService artistService;

    protected AlbumDeserializer(Class<?> vc) {
        super(vc);
    }

    protected AlbumDeserializer() {
        this(null);
    }

    @Override
    public Album deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String name = null;
        String notes = null;

        Integer year = null;
        Artist artist = null;
        if (node.hasNonNull("name")) {
            name = node.get("name").asText();
        }
        if (node.hasNonNull("year")) {
            year = ValidateCurrentYear.ValidateYear(node.get("year").asInt());
        }
        if (node.hasNonNull("notes")) {
            notes = node.get("notes").asText();
        }
        if (node.hasNonNull("artist")) {
            artist = artistService.getOne(node.get("artist").asInt());
        }

        return new Album(name, year, notes, artist);
    }
}
