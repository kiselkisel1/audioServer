package com.example.application.model;



import com.example.application.deserializer.ArtistDeserializer;
import com.example.application.serializer.ArtistSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@JsonSerialize(using = ArtistSerializer.class)
@JsonDeserialize(using = ArtistDeserializer.class)
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

     private int start_activity_year;

     private int end_activity_year;

     @ManyToMany
     @JoinTable(name = "handbook",
            joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private Set<Genre> genres;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
    private Set<Song> songs;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
    private Set<Album> albums;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStart_activity_year() {
        return start_activity_year;
    }

    public void setStart_activity_year(int start_activity_year) {
        this.start_activity_year = start_activity_year;
    }

    public Integer getEnd_activity_year() {
        return end_activity_year;
    }

    public void setEnd_activity_year(int end_activity_year) {
        this.end_activity_year = end_activity_year;
    }

    public Artist() {
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Artist(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes, int start_activity_year, int end_activity_year, Set<Genre> genres) {

        this.name = name;
        this.notes = notes;
        this.start_activity_year = start_activity_year;
        this.end_activity_year = end_activity_year;
        this.genres = genres;
    }

    public Artist(@NotBlank(message = "Name is required") @Size(min = 5, max = 200, message = "Name should contain from 5 to 200 symbols") String name, @Size(max = 2000, message = "Notes should contain less than 2000 symbols") String notes, int start_activity_year, int end_activity_year) {
        this.name = name;
        this.notes = notes;
        this.start_activity_year = start_activity_year;
        this.end_activity_year = end_activity_year;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", start_activity_year=" + start_activity_year +
                ", end_activity_year=" + end_activity_year +
                ", genres=" + genres +
                '}';
    }
}
