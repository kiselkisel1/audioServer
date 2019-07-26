package com.example.application.controller;

import com.example.application.model.Genre;
import com.example.application.repository.GenreRepository;
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
    public void createGenre() throws Exception {

        Genre genre =new Genre(1,"genre1");
//забыла указать поведение мока
        given(genreRepository.save(any())).willReturn(genre);

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/genres")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(genre))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(genre.getName())))
                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        logger.debug("content= "+content);

    }


    @Test
    public void updateProduct() throws Exception {
        Genre genre =new Genre("genre2");

        given(genreRepository.save(any())).willReturn(genre );

        mockMvc.perform(MockMvcRequestBuilders.put("/genres/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(genre))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(genre.getName())))
                .andDo(print());
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                delete("/genres/2"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
