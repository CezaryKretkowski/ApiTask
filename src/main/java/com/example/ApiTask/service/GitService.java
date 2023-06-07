package com.example.ApiTask.service;
import com.example.ApiTask.dtos.BranchDto;
import com.example.ApiTask.dtos.RepositoryDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class GitService {
    private final String gitHubUrl = "https://api.github.com";
    private final RestTemplate restTemplate;

    public GitService(){
        this.restTemplate = new RestTemplate();
    }



    private Optional<List<RepositoryDto>> getBranches(List<RepositoryDto> repositories,String username){
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(gitHubUrl).path("/repos/{username}/").buildAndExpand(username);
        String apiUrl = uriComponents.toUriString();
        for (RepositoryDto repository : repositories){
            String url = apiUrl +repository.getName()+"/branches";
            ResponseEntity<BranchDto[]> response;
            try {
                response = restTemplate.exchange(url, HttpMethod.GET, null, BranchDto[].class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    repository.setBranches(Arrays.asList(response.getBody()));
                } else {
                    repository.setBranches(null);
                }

            }catch (HttpClientErrorException ex){
                repository.setBranches(null);
            }

        }
        return Optional.of(repositories);
    }

    public Optional<List<RepositoryDto>> getRepository(String username){

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(gitHubUrl).path("/users/{username}/repos").buildAndExpand(username);
        String apiUrl = uriComponents.toUriString();
        Map<String, String> params = new HashMap<String, String>();
        params.put("type","owner");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<RepositoryDto[]> response;
        try{
            response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, RepositoryDto[].class,params);
            if (response.getStatusCode().is2xxSuccessful()) {
                return getBranches(Arrays.asList(response.getBody()),username);
            } else {
                return Optional.empty();
            }
        }catch (HttpClientErrorException ex){
            return Optional.empty();
        }

    }
}
