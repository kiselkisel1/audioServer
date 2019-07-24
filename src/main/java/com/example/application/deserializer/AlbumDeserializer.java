package com.example.application.deserializer;

import com.example.application.model.Album;
import com.example.application.model.Artist;
import com.example.application.service.ArtistService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

        String name = node.get("name").asText();
        int year =  node.get("year").asInt();
        String notes=null;
        Artist artist=null;
        if(node.hasNonNull("notes")  ){
            notes = node.get("notes").asText();
        }
        if(node.hasNonNull("artist")  ){
            artist=artistService.getOne(node.get("artist").asInt());
        }

        return new Album(name,year,notes,artist);
    }
}
