package com.example.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {

    @Autowired
    Environment environment;

     @Bean
    public String uploadPath() {

        return environment.getProperty("file.uploadPath");
    }

//    @Bean
//    public String getPath(@Value("${file.uploadPath}") String uploadPath) {
//
//        return uploadPath;
//    }

}
