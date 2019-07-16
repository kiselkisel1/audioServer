//package com.example.application.exceptions;
//
//import jdk.internal.loader.Resource;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.sql.SQLException;
//
//@ControllerAdvice
//public class ArtistServiceErrorAdvise {
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler({ResourceNotFoundException.class})
//    public void handle(ResourceNotFoundException e) {}
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({NotFoundException.class, SQLException.class, NullPointerException.class})
//    public void handle() {}
//
//
//}
