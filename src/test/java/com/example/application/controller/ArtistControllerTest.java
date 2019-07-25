package com.example.application.controller;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.service.ArtistService;
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

import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @MockBean
    ArtistService artistService;

    @Autowired
    private MockMvc mockMvc;


    private static final Logger logger = LoggerFactory.getLogger(Artist.class);


    @Test
    public void getArtistByIdTestReturn200() throws Exception {
        Artist artist = new Artist(45, "artist1", "artist2", 1900, 2000, new Genre(1, "genre1"));
        given(artistService.getOne(any())).willReturn(artist);

         mockMvc.perform(get("/artists/45"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(artist.getName()))) ;
    }



    @Test
    public void createArtist() throws Exception {
        Artist artist = new Artist(4, "artist1", "artist2", 1902, 2000, new Genre(3, "genre1"));

//        given(artistService.save(any())).willReturn(artist);

        String json = "{\n" +
//                "        \"id\": 4,\n" +
                "        \"name\": \"artist1\",\n" +
                "        \"notes\": \"artist2\",\n" +
                "        \"start_activity_year\": 1902,\n" +
                "        \"end_activity_year\": 2000,\n" +
                "        \"genre\": 3\n" +
                "    }";

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/artists")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(artist))
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("name", is(artist.getName())))
                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        logger.debug("content= "+content);
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}