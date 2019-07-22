package com.example.application.deserializer;

import com.example.application.model.Album;
import com.example.application.model.Artist;
import com.example.application.service.ArtistService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AlbumDeserializer extends StdDeserializer<Album> {

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

        Set<Artist>artists=new HashSet<>();

        String name = node.get("name").asText();
        int year =  node.get("year").asInt();
        String notes = node.get("notes").asText();

        if(node.get("artists").isArray()){
            for (final JsonNode objNode : node.get("artists")) {

                Artist artist=artistService.getOne(objNode.asInt());
                 artists.add(artist);
            }
        }
        return new Album(name,year,notes,artists);
    }
}
