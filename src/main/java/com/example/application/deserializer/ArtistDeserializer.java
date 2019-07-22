package com.example.application.deserializer;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.repository.GenreRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ArtistDeserializer extends StdDeserializer<Artist> {

    private static final Logger logger = LoggerFactory.getLogger(Artist.class);

    @Autowired
    GenreRepository genreRepository;

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

        Set<Genre>genres=new HashSet<>();

        String name = node.get("name").asText();
        String notes = node.get("notes").asText();
        int start_activity_year =  node.get("start_activity_year").asInt();
        int end_activity_year = (Integer) ((IntNode) node.get("end_activity_year")).numberValue();
 //        JsonNode js=node.get("genres");
        if(node.get("genres").isArray()){
            for (final JsonNode objNode : node.get("genres")) {
                logger.debug("objNode="+objNode);
                Genre genre=genreRepository.getOne(objNode.asInt());
                logger.debug("genre"+genre.toString());
                genres.add(genre);
            }
        }
        return new Artist(name,notes,start_activity_year,end_activity_year,genres);
    }
}
