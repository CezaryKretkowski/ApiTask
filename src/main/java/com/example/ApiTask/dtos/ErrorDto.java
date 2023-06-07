package com.example.ApiTask.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
/**
 * ErrorDto -  is a data transfer object to store information about error.
 * */
@Getter
@Setter
public class ErrorDto {
    private HttpStatus status;
    private String message;

    public ErrorDto(HttpStatus status,String message){
        this.status=status;
        this.message=message;
    }
}
