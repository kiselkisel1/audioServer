package com.example.application.model;

import com.example.application.deserializer.AlbumDeserializer;
import com.example.application.serializer.AlbumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@JsonSerialize(using = AlbumSerializer.class)
@JsonDeserialize(using = AlbumDeserializer.class)
@Data
@NoArgsConstructor
@Entity
@Table
public class Album {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    @Size(max=200,message = "Name should contain symbols")
    private String name;

     private Integer year;

    @Size(max=2000,message = "Notes should contain less than 2000 symbols")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artist_id")
    private Artist artist;



    public Album(@Size(max = 200, message = "Name should contain 200 symbols") String name, Integer year, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes, Artist artist) {
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.artist = artist;
    }

    public Album(@Size(max = 200, message = "Name should contain symbols") String name, Integer year, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes) {
        this.name = name;
        this.year = year;
        this.notes = notes;
    }
    public Album(@Size(max = 200, message = "Name should contain 200 symbols") String name , Artist artist) {
        this.name = name;
        this.artist = artist;
    }
}