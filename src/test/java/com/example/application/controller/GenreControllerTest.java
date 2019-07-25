package com.example.application.controller;

import com.example.application.model.Album;
import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.repository.GenreRepository;
import com.example.application.service.ArtistService;
import com.example.application.service.GenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @MockBean
    GenreRepository genreRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final Logger logger = LoggerFactory.getLogger(Genre.class);


    @Test
    public void createArtist() throws Exception {

        String inputJson=" {\n" +

                "        \"name\": \"genre1\"\n" +
                "    }";

        Genre genre =new Genre("genre1");
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/genres")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(genre))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
//                .andReturn();
//                .andExpect(jsonPath("$[1].name", is(genre.getName())));

//        MvcResult mvcResult2 = mockMvc.
//                perform(MockMvcRequestBuilders.post("/genres")
//                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)
//                .characterEncoding("utf-8"))
//                .andDo(print())
//                .andReturn();



        String content = mvcResult.getResponse().getContentAsString();
        logger.debug("content= "+content);
//        assertEquals(content, genre.toString());

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
