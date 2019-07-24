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

//    @Query(value = "SELECT * FROM artist where name LIKE %:name% or :year between start_activity_year and end_activity_year or genre_id = :genreId", nativeQuery = true)
//    @Query("select b from Song b where b.full_price <= :price and b.resort = :resort")
//    List<Song> filter(@Param("name") String name, @Param("year") int year , @Param("genreId") int genreId);


}