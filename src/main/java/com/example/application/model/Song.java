package com.example.application.model;


import com.example.application.serializer.SongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonSerialize(using = SongSerializer.class)
//@JsonDeserialize(using = SongDeserializer.class)
@Entity
@Table
@Data
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name should contain not more than 200 symbols")
    private String name;

    private Integer year;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "storage_id")
    private Storage storage;

    private Long size;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "album_id")
    private Album album;


    public Song(@NotBlank(message = "Name is required") @Size( max = 200, message =  "Name should contain not more than 200 symbols") String name, Integer year, Storage storage, Long size, String path, Album album) {
        this.name = name;
        this.year = year;
        this.storage = storage;
        this.size = size;
        this.path = path;
        this.album = album;
    }
}
