package com.example.application.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.omg.CORBA.INTERNAL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table
public class Artist {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min=5,max=200,message = "Name should contain from 5 to 200 symbols")
    private String name;

    @Size(max=2000,message = "Notes should contain less than 2000 symbols")
    private String notes;

     private Integer startActivityYear;

     private Integer endActivityYear;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id")
    private Set<Genre> genres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getStartActivityYear() {
        return startActivityYear;
    }

    public void setStartActivityYear(Integer startActivityYear) {
        this.startActivityYear = startActivityYear;
    }

    public Integer getEndActivityYear() {
        return endActivityYear;
    }

    public void setEndActivityYear(Integer endActivityYear) {
        this.endActivityYear = endActivityYear;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", startActivityYear=" + startActivityYear +
                ", endActivityYear=" + endActivityYear +
                ", genres=" + genres +
                '}';
    }
}
