package com.example.application.model;
 import com.example.application.deserializer.AlbumDeserializer;
 import com.example.application.serializer.AlbumSerializer;
 import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
 import com.fasterxml.jackson.databind.annotation.JsonSerialize;

 import javax.persistence.*;
import javax.validation.constraints.NotBlank;
 import javax.validation.constraints.Size;
 import java.util.Set;

@Entity
@Table
@JsonSerialize(using = AlbumSerializer.class)
@JsonDeserialize(using = AlbumDeserializer.class)
public class Album {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(min=5,max=200,message = "Name should contain from 5 to 200 symbols")
    private String name;

     private int year;

    @Size(max=2000,message = "Notes should contain less than 2000 symbols")
    private String notes;

    @ManyToMany
    @JoinTable(name = "handbook",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    private Set<Artist> artists;

    @ManyToMany
    @JoinTable(name = "handbook",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private Set<Genre> genres;

    public Album() {
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Album(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, int year, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes, Set<Artist> artists) {
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.artists = artists;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }
}