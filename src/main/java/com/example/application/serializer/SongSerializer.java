package com.example.application.serializer;

import com.example.application.model.Artist;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SongSerializer extends StdSerializer<Artist> {

    public SongSerializer(Class<Artist> t) {
        super(t);
    }

    @Override
    public void serialize(Artist artist, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
