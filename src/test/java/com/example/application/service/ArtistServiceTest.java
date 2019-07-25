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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistServiceTest {
    @MockBean
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    @Test
    public void testGetById() {
        given(artistRepository.findById(any()))
                .willReturn(java.util.Optional.of(new Artist(45, "artist1", "artist2",
                        1900, 2000, new Genre(1, "genre1"))));


      Artist artist=artistService.getOne(45);
        assertThat(artist.getId()).isEqualTo(45);
        // и какие-нибудь другие проверки
    }

}
