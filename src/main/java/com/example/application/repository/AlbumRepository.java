package com.example.application.repository;

import com.example.application.model.Album;
import com.example.application.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Integer> {


    @Query(value="select*from album where name LIKE %:name% or year = :year or artist_id In :artists or artist_id in " +
            "(select id from artist where genre_id in :genres)", nativeQuery = true)
    List<Album> filter(@Param("name") String name, @Param("year") int year  ,@Param("artists") Integer[] artists,@Param("genres") Integer[] genres);
}