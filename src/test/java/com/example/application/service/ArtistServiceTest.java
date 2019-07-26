package com.example.application.service;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.repository.ArtistRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistServiceTest {
    @MockBean
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    Artist artist = new Artist( 4,"artist1", "artist2", 1902, 2000, new Genre( 2,"genre1"));

    @Test
    public void testGetById() {

        given(artistRepository.findById(any())).willReturn(java.util.Optional.ofNullable(artist));
        Artist created=artistService.getOne(4);
        assertEquals(created.getName(),artist.getName());

    }


    @Test
    public void saveUser_returnUser(){

        given(artistRepository.save(any(Artist.class))).willReturn(artist);
        Artist created=artistService.save(artist);
        assertEquals(created.getName(),artist.getName());
        verify(artistRepository, times(1)).save(artist);
    }

    @Test
    public void getAll(){
        List<Artist> list=new ArrayList<>();
        list.add(artist);
        list.add(new Artist(42, "artist2", "artist3", 1900, 2000, new Genre(2, "genre3")));

        given(artistRepository.findAll()).willReturn(list);

        List<Artist> artistList=artistService.getAll();

        assertEquals(artistList.size(),2);

        verify(artistRepository, times(1)).findAll();
    }

    @Test
    public void delete(){

        artistService.delete(artist);
        verify(artistRepository, times(1)).delete(artist);

    }

    @Test
    public void filter(){
        List<Artist> list=new ArrayList<>();
        list.add(artist);
        list.add(new Artist(1, "artist2", "artist3", 1900, 2000, new Genre(2, "genre3")));

        Integer[]genres=new Integer[]{1,4};
        given(artistRepository.filter("artist1",1900,genres)).willReturn(list);

        List<Artist> created=artistService.filter("artist1",1900,genres);

        assertEquals(created.size(),2);

        verify(artistRepository, times(1)).filter("artist1",1900,genres);

    }
}
