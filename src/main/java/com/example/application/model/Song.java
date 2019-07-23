package com.example.application.model;


import com.example.application.deserializer.ArtistDeserializer;
import com.example.application.deserializer.SongDeserializer;
import com.example.application.serializer.ArtistSerializer;
import com.example.application.serializer.SongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonSerialize(using = SongSerializer.class)
@JsonDeserialize(using = SongDeserializer.class)
@Entity
@Table
@Data
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(min=5,max=200,message = "Name should contain from 5 to 200 symbols")
    private String name;

    //    @Range(min=1900, max=Year.now().getValue())
    private int year;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    public Song(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, int year, String comment, Album album) {
        this.name = name;
        this.year = year;
        this.comment = comment;
        this.album = album;
    }
}
