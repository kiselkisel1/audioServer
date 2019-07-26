package com.example.application.model;
 import com.example.application.deserializer.AlbumDeserializer;
 import com.example.application.exceptions.CustomException;
 import com.example.application.serializer.AlbumSerializer;
 import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @NotBlank(message = "Name is required")
    @Size(min=5,max=200,message = "Name should contain from 5 to 200 symbols")
    private String name;

    @Min(1900)
     private int year;

    @Size(max=2000,message = "Notes should contain less than 2000 symbols")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public void setYear(int year) {
        if(year> Calendar.getInstance().get(Calendar.YEAR)){
            throw new CustomException("Year should be more from 1900 to current year");
        }
        this.year = year;
    }

    public Album(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, int year, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes, Artist artist) {
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.artist = artist;
    }
}