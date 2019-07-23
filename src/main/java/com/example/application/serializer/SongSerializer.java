package com.example.application.serializer;

import com.example.application.model.Artist;
import com.example.application.model.Song;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SongSerializer extends StdSerializer<Song> {

    public SongSerializer(Class<Song> t) {
        super(t);
    }

    public SongSerializer() {
        this(null);
    }

    @Override
    public void serialize(Song song, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", song.getId());
        jsonGenerator.writeStringField("name", song.getName());
        jsonGenerator.writeNumberField("year", song.getYear());
        jsonGenerator.writeStringField("comment", song.getComment());
        jsonGenerator.writeNumberField("artist",song.getAlbum().getArtist().getId());
        jsonGenerator.writeNumberField("album",song.getAlbum().getId());
        jsonGenerator.writeNumberField("genre",song.getAlbum().getArtist().getGenre().getId());
        jsonGenerator.writeEndObject();

    }
}
