package com.example.application.repository;

import com.example.application.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

     @Query(value = "SELECT * FROM artist where name LIKE %:name% or :year between start_activity_year and end_activity_year or genre_id In :genres", nativeQuery = true)
     List<Artist> filter(@Param("name") String name, @Param("year") int year , @Param("genres") Integer[] genres);

}
