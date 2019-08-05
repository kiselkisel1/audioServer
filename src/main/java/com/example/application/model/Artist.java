package com.example.application.model;


import com.example.application.deserializer.ArtistDeserializer;
import com.example.application.exceptions.CustomException;
import com.example.application.serializer.ArtistSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Set;

@JsonSerialize(using = ArtistSerializer.class)
@JsonDeserialize(using = ArtistDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     @Size(max = 200, message = "Name should contain 200 symbols")
    private String name;

    @Size(max = 2000, message = "Notes should contain less than 2000 symbols")
    private String notes;

    private Integer start_activity_year;

    private Integer end_activity_year;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Artist( @Size(max = 200, message = "Name should contain from 5 to 200 symbols") String name, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes, Integer start_activity_year,Integer end_activity_year, Genre genre) {
        this.name = name;
        this.notes = notes;
        this.start_activity_year = start_activity_year;
        this.end_activity_year = end_activity_year;
        this.genre = genre;
    }


}
