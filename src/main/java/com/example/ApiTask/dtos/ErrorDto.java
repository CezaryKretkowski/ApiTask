package com.example.ApiTask.dtos;

import org.springframework.http.HttpStatusCode;

public record ErrorDto(int status, String message) {
}
