package com.example.application.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(),status.value(),ex.getMessage());
        errors.setFieldErrors(ex.getBindingResult().getFieldErrors());
//       errors.addValidationList(ex.getBindingResult().getFieldErrors());
        return new ResponseEntity(errors,HttpStatus.BAD_REQUEST);
    }

    //exception 400 if json is not valid.In controller it is annotated @Valid
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
         CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(),status.value(),ex.getMessage());
         return new ResponseEntity(errors, status);
    }




}

