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

    @Query(value = "SELECT * FROM artist where name LIKE %:name%  or :year between start_activity_year and end_activity_year or artist_id In :artists", nativeQuery = true)
    List<Album> filter(@Param("name") String name, @Param("year") int year  ,@Param("artists") Integer[] artists);
}