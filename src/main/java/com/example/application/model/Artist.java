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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(min=5,max=200,message = "Name should contain from 5 to 200 symbols")
    private String name;

    @Size(max=2000,message = "Notes should contain less than 2000 symbols")
    private String notes;

    @Min(1900)
     private int start_activity_year;

     @Min(1900)
     private int end_activity_year;

     public void setEnd_activity_year(int end_activity_year) {

         if(end_activity_year>Calendar.getInstance().get(Calendar.YEAR)){
             throw new CustomException("Year should be more from 1900 to current year");
         }

         this.end_activity_year = end_activity_year;
     }

     @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Artist(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes, int start_activity_year, int end_activity_year, Genre genre) {
        this.name = name;
        this.notes = notes;
        this.start_activity_year = start_activity_year;
        this.end_activity_year = end_activity_year;
        this.genre = genre;
    }


}
