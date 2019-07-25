package com.example.application.controller;

import com.example.application.model.Artist;
import com.example.application.model.Genre;
import com.example.application.service.ArtistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @MockBean
    ArtistService artistService;

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void getArtistByIdTestReturn200() throws Exception {
        Artist artist = new Artist(45, "artist1", "artist2", 1900, 2000, new Genre(1, "genre1"));
        given(artistService.getOne(any())).willReturn(artist);

        mockMvc.perform(get("/artists/45"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(artist.getName())));

    }



    @Test
    public void createArtist() throws Exception {
        Artist artist = new Artist( 2,"artist1", "artist2", 1900, 2000, new Genre(3,"genre1"));
        String json="  {\n" +
                "        \"name\": \"errwer\",\n" +
                "        \"notes\": \"werwer\",\n" +
                "        \"start_activity_year\": 2000,\n" +
                "        \"end_activity_year\": 2010,\n" +
                "        \"genre\": 3\n" +
                "    }";

//        mockMvc.perform(post("/artists")
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("name", is(artist.getName())));



         mockMvc.perform(MockMvcRequestBuilders.post("/artists")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andReturn();
    }
//void s () {
//
//    String uri = "/products";
//    Product product = new Product();
//    product.setId("3");
//    product.setName("Ginger");
//
//    String inputJson = super.mapToJson(product);
//    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.
//            post(uri)
//            .contentType(MediaType.APPLICATION_JSON_VALUE).
//                    content(inputJson)).andReturn();
//
//    int status = mvcResult.getResponse().getStatus();
//    assertEquals(201, status);
//    String content = mvcResult.getResponse().getContentAsString();
//    assertEquals(content, "Product is created successfully");
//}


}