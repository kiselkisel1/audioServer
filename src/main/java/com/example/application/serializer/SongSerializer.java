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
        if (song.getName() != null) {
            jsonGenerator.writeStringField("name",song.getName());
        }

        if (song.getYear() != null) {
            jsonGenerator.writeNumberField("year", song.getYear());
        }
        if(song.getComment()!=null) {
            jsonGenerator.writeStringField("comment", song.getComment());
        }
        if(song.getAlbum().getArtist()!=null){
            jsonGenerator.writeNumberField("artist",song.getAlbum().getArtist().getId());
        }
        if(song.getAlbum()!=null){
            jsonGenerator.writeNumberField("album",song.getAlbum().getId());
        }
        if(song.getAlbum().getArtist().getGenre()!=null){
            jsonGenerator.writeNumberField("genre",song.getAlbum().getArtist().getGenre().getId());
        }

        jsonGenerator.writeEndObject();

    }
}
