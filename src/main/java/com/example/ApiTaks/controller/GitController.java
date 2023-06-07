package com.example.ApiTaks.controller;

import com.example.ApiTaks.dtos.ErrorDto;
import com.example.ApiTaks.dtos.RepositoryDto;
import com.example.ApiTaks.service.GitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class GitController {
    private final GitService gitService;
    public  GitController(GitService service){
        gitService=service;
    }
    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getGitUserRepositories(@PathVariable String username,@RequestHeader("Accept") String acceptHeader){
        var list =  gitService.getRepository(username);

        if(list.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(HttpStatus.NOT_FOUND,"User Not Found"));
        }
        if(acceptHeader != null && acceptHeader.contains("application/xml")){
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorDto(HttpStatus.NOT_ACCEPTABLE,"Bad Format"));
        }

        return ResponseEntity.ok(list.get());
    }
}
