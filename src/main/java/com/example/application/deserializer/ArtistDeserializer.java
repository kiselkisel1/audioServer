package com.example.application.deserializer;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.service.GenreService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
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
    String notes=null;
    int end_activity_year=0;
    Genre genre=null;

     if(node.hasNonNull("notes")  ){
         notes = node.get("notes").asText();
     }
     if(node.hasNonNull("end_activity_year")){
         end_activity_year=node.get("end_activity_year").asInt();
     }

     if(node.hasNonNull("genre")){
         genre=genreService.getOne(node.get("genre").asInt());
     }
    int start_activity_year =  node.get("start_activity_year").asInt();

     return new Artist(name,notes,start_activity_year,end_activity_year,genre);
}
}
