package com.example.application.controller;

import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.service.ArtistService;
import com.example.application.service.GenreService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @MockBean
    ArtistService artistService;

    @MockBean
    GenreService genreService;

    @Autowired
    private MockMvc mockMvc;


    private static final Logger logger = LoggerFactory.getLogger(Artist.class);

    Artist artist = new Artist(4, "artist1", "artist2", 1900, 2000, new Genre(1, "genre1"));


    @Test
    public void getArtistById() throws Exception {
        given(artistService.getOne(any())).willReturn(artist);

        mockMvc.perform(get("/artists/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(artist.getName())));
    }

    @Test
    public void getArtistByIdReturn404() throws Exception {
         when(artistService.getOne(5)).thenThrow(new ResourceNotFoundException("ARTIST_DOES_NOT_EXISTS"));
        mockMvc.perform(get("/artists/5"))
                .andExpect(status().is4xxClientError());
     }

    @Test
    public void getArtistByIdReturn500() throws Exception {
        when(artistService.getOne(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/artists/5"))
                .andExpect(status().isInternalServerError());

     }


    @Test
    public void getAll() throws Exception {

        List<Artist> list = new ArrayList<>();
        list.add(artist);
        list.add(new Artist(42, "artist2", "artist3", 1900, 2000, new Genre(2, "genre3")));

        given(artistService.getAll()).willReturn(list);

        String result = asJsonString(list);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/artists")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, result);


        logger.debug("content = " + content);

    }




    @Test
    public void createArtist() throws Exception {

        given(artistService.save(any())).willReturn(artist);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/artists")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(artist))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(artist.getName())))
                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        logger.debug("content= " + content);
    }

    @Test
    public void createArtistThrows400() throws Exception {

        Artist artist2 = new Artist(4, "", "artist2", 1800, 2000, new Genre(1, "genre1"));

        given(artistService.save(any())).willReturn(artist2);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/artists")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(artist2))
                .characterEncoding("utf-8"))
                .andExpect(status().is4xxClientError())
                 .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        logger.debug("content= " + content);
    }

    @Test
    public void updateArtist() throws Exception {

        given(artistService.getOne(any())).willReturn(artist);
        given(artistService.save(any())).willReturn(artist);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/artists/4")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(artist))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(artist.getName())))
                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        logger.debug("content= " + content);
    }

    @Test
    public void deleteProduct() throws Exception {

        given(artistService.getOne(any())).willReturn(artist);

        mockMvc.perform(delete("/artists?id=4"))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteProductThrowsBadRequest() throws Exception {

        given(artistService.getOne(any())).willReturn(artist);

        mockMvc.perform(delete("/artists?ids=4"))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}