package com.example.ApiTask.controller;

import com.example.ApiTask.dtos.ErrorDto;
import com.example.ApiTask.service.GitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * GitController - is the controller that is responsible for communicating with the user.
 * */
@RestController
public class GitController {

    /**
     * gitService - is instance of service which  get gitHub repositories for specify user.
     * */
    private final GitService gitService;
    public  GitController(GitService service){
        gitService=service;
    }

    /**
     *getGitUserRepositories - supports the get method and returns a list of repositories for a given user.
     * @param username  - name od user.
     * @return - List of repositories. If user is not on gitHub, method return status code 404. If user past in headers "Accept:application/xml", method return status code 406
     * */
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
