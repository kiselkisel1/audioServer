package com.example.application.repository;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Query("select u from Genre u where u.name = ?1")
    Genre findByName(String name);
}
