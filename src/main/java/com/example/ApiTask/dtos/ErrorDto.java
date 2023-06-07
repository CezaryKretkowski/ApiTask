package com.example.ApiTask.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

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
