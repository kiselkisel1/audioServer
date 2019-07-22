package com.example.application.serializer;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ArtistSerializer extends StdSerializer<Artist> {

    private static final Logger logger = LoggerFactory.getLogger(Artist.class);

    public ArtistSerializer(Class<Artist> t) {
        super(t);
    }

    public ArtistSerializer() {
       this(null);
    }
    @Override
    public void serialize(Artist artist, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", artist.getId());
        jsonGenerator.writeStringField("name", artist.getName());
        jsonGenerator.writeStringField("notes", artist.getNotes());
        jsonGenerator.writeNumberField("start_activity_year", artist.getStart_activity_year());
        jsonGenerator.writeNumberField("end_activity_year", artist.getEnd_activity_year());
        jsonGenerator.writeFieldName("genres");
        jsonGenerator.writeStartArray();
        for(Genre genre:artist.getGenres()){
            logger.debug("inside write number");
            jsonGenerator.writeNumber(genre.getId());
        }
         jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
