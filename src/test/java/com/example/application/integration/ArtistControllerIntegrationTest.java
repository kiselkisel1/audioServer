package com.example.application.integration;

import com.example.application.Application;
import com.example.application.model.Artist;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtistControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    @Test
    public void contextLoads() {

    }

    private static final Logger logger = LoggerFactory.getLogger(Artist.class);


    private  HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return  headers;
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

//    @After
//    public void resetDb() {
//        artistRepository.deleteAll();
//        artistRepository.flush();
//    }


    @Test
    public void getAll() {


        HttpEntity<String> entity = new HttpEntity<String>(null, setHeader());
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/artists"),
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());

    }


    @Test
    public void getOne() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, setHeader());
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/artists/63"), HttpMethod.GET, entity, String.class);

        logger.debug("RESPONSE BODY " + response.getBody());
        String expected = "{\"id\":63,\"name\":\"Death Grips\" ,\"notes\":\"notes\",\"genre\":37}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void saveArtistAndThrow404() {
        String expected = "{\"name\":\"Death Grips\",\"start_activity_year\":2010,\"genre\":17}";


         HttpEntity<String> entity = new HttpEntity<>(expected, setHeader());

        ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/artists"), entity, String.class);

        logger.debug("STATUS CODE " + response.getStatusCode().toString());
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());


    }

    @Test
    public void saveArtistAndReturn200() {

        String expected = "{\"name\":\"Death Grips\",\"notes\":\"notes\",\"genre\":37}";


        HttpEntity<String> entity = new HttpEntity<>(expected, setHeader());

        ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/artists"), entity, String.class);

        logger.debug(response.toString());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        try {
            JSONAssert.assertEquals(expected, response.getBody(), false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateArtistAndReturn200(){
        String expected = "{ \"name\":\"Hello\",\"notes\":\"notes\",\"genre\":37}";


        HttpEntity<String> entity = new HttpEntity<>(expected, setHeader());

        ResponseEntity<String> response = restTemplate.exchange("/artists/{id}", HttpMethod.PUT, entity, String.class,64);

        logger.debug(response.toString());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        try {
            JSONAssert.assertEquals(expected, response.getBody(), false);
        } catch (JSONException e) {
            e.printStackTrace();
        }      }



}
