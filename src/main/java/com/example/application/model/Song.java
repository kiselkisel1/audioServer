package com.example.application.model;


import com.example.application.deserializer.SongDeserializer;
import com.example.application.exceptions.CustomException;
import com.example.application.serializer.SongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Calendar;

@JsonSerialize(using = SongSerializer.class)
//@JsonDeserialize(using = SongDeserializer.class)
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

     private int year;

    private String comment;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "album_id")
    private Album album;


    public Song(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, int year, String comment, Album album) {
        this.name = name;
        this.year = year;
        this.comment = comment;
        this.album = album;
    }

    public Song(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, int year, String comment, String path, Album album) {
        this.name = name;
        this.year = year;
        this.comment = comment;
        this.path = path;
        this.album = album;
    }
}
