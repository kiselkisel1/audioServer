package com.example.application;

import org.apache.tika.exception.TikaException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import java.io.IOException;

@SpringBootApplication
public class Application {


    public static void main(String[] args) throws IOException, TikaException, SAXException {


        SpringApplication.run(Application.class, args);
    }

}
