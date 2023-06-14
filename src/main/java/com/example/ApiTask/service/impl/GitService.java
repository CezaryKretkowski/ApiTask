package com.example.ApiTask.service.impl;
import com.example.ApiTask.dtos.BranchDto;
import com.example.ApiTask.dtos.CommitDto;
import com.example.ApiTask.dtos.RepositoryDto;
import com.example.ApiTask.exception.UserNotFoundException;
import com.example.ApiTask.service.IGitService;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class GitService implements IGitService {

    private final String gitHubUrl = "https://api.github.com";

    private final WebClient webClient;

    public GitService(WebClient client){
        this.webClient=client;
    }
    
    private List<RepositoryDto> getBranches(List<RepositoryDto> repositories,String username){
        List<RepositoryDto> repos = new ArrayList<>();
        for (RepositoryDto repository : repositories){
            String repo = repository.name();
            URI uri = UriComponentsBuilder.fromHttpUrl(gitHubUrl).path("/repos/{username}/{repo}/branches").buildAndExpand(username,repo).toUri();
            List<BranchDto> branches = webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(status->status.value()!=HttpStatus.OK.value(),
                            response->Mono.error(new ResponseStatusException(response.statusCode())))
                    .bodyToFlux(BranchDto.class).collectList().block();
            repos.add(new RepositoryDto(repository.name(),repository.htmlUrl(),branches));
        }
        return repos;
    }

    @Override
    public List<RepositoryDto> getRepositories(String username) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gitHubUrl).path("/users/{username}/repos").buildAndExpand(username).toUri();
        List<RepositoryDto> list = webClient.get().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("type","owner")
                .retrieve()
                .onStatus(status-> status.value() == HttpStatus.NOT_FOUND.value(),response->Mono.error(new UserNotFoundException("User not found!")))
                .bodyToFlux(RepositoryDto.class).collectList().block();
        return  getBranches(list,username);

    }


}
