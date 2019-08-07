package com.example.application.serializer;

import com.example.application.model.Album;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AlbumSerializer extends StdSerializer<Album> {

    public AlbumSerializer(Class<Album> t) {
        super(t);
    }

    public AlbumSerializer() {
        this(null);
    }

    @Override
    public void serialize(Album album, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", album.getId());
        if (album.getName() != null) {
            jsonGenerator.writeStringField("name", album.getName());
        }

        if (album.getYear() != null) {
            jsonGenerator.writeNumberField("year", album.getYear());
        }
        if (album.getNotes() != null) {
            jsonGenerator.writeStringField("notes", album.getNotes());
        }
        if (album.getArtist() != null) {
            jsonGenerator.writeNumberField("artist", album.getArtist().getId());
            if (album.getArtist().getGenre() != null) {
                jsonGenerator.writeNumberField("genre", album.getArtist().getGenre().getId());
            }
        }

        jsonGenerator.writeEndObject();
    }
}
