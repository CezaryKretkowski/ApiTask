package com.example.ApiTask.controller;


import com.example.ApiTask.dtos.RepositoryDto;
import com.example.ApiTask.service.IGitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GitController {
    private final IGitService gitService;
    public  GitController(IGitService gitService){
        this.gitService=gitService;
    }

    @GetMapping(path="/repositories/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RepositoryDto> getGitUserRepositories(@PathVariable String username){
        return gitService.getRepositories(username);
    }
}
