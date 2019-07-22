package com.example.application.serializer;

import com.example.application.model.Album;
import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AlbumSerializer extends StdSerializer<Album> {
    protected AlbumSerializer(Class<Album> t) {
        super(t);
    }

    @Override
    public void serialize(Album album, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", album.getId());
        jsonGenerator.writeStringField("name", album.getName());
        jsonGenerator.writeNumberField("year", album.getYear());
        jsonGenerator.writeStringField("notes", album.getNotes());

        jsonGenerator.writeFieldName("artists");
        jsonGenerator.writeStartArray();
        for(Artist artist:album.getArtists()){
             jsonGenerator.writeNumber(artist.getId());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
