package com.example.application.repository;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {


    @Query(value="select*from song where name LIKE %:name% or year = :year or album_id In (select id from album where artist_id in :artists)" +
            " or album_id In (select id from album where artist_id" +
            " in (select id from artist where genre_id in :genres))", nativeQuery = true)
    List<Song> filter(@Param("name") String name, @Param("year") int year  ,@Param("artists") Integer[] artists,@Param("genres") Integer[] genres);

    @Query("select u from Song u where u.name = :name")
    Song findByName(@Param("name") String name);

}