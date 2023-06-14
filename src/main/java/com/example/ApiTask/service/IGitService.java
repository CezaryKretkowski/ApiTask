package com.example.ApiTask.service;

import com.example.ApiTask.dtos.RepositoryDto;

import java.util.List;

public interface IGitService {
    List<RepositoryDto> getRepositories(String username);

}
