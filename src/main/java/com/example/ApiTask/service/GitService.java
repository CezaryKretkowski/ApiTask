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

/**
 * GitService class is a service which aim is get information from gitHub api and past it to controller.
 */
@Service
public class GitService {
    /**
     * gitHubUrl - is base url to gitHub api.
     */
    private final String gitHubUrl = "https://api.github.com";

    /**
     * restTemplate - is an object of the RestTemplate class by means of which http methods are executed.
     * */
    private final RestTemplate restTemplate;

    /**
     * class constructor.
     * */
    public GitService(){
        this.restTemplate = new RestTemplate();
    }

    /**
     * getBranches - is a method whose purpose is to download all branches for a specific repository.
     * @param repositories - is List of repositories for specific user.
     * @param username - is name of user.
     * @return - returns updated list of user repository.
     * */
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

    /**
     * getRepository - is a method whose purpose is to download all repositories for a specific user.
     * @param username - is name of user.
     * @return - returns updated list of user repository.
     * */
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
