package com.example.ApiTask.exception;

import com.example.ApiTask.dtos.ErrorDto;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {HttpMediaTypeException.class})
    public ResponseEntity<Object> handleNotAcceptableException(HttpMediaTypeException e){
        var header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ErrorDto error = new ErrorDto(
                HttpStatus.NOT_ACCEPTABLE.value(),
                e.getMessage()
        );
        return new ResponseEntity(error,header,HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(UserNotFoundException ex){
        var header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ErrorDto error = new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity(error,header,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<Object> handleException(ResponseStatusException ex)
    {
        var header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ErrorDto error = new ErrorDto(
                ex.getStatusCode().value(),
                ex.getMessage()
        );
        return new ResponseEntity(error,header,ex.getStatusCode());
    }

}
