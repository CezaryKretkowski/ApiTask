package com.example.ApiTask.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record RepositoryDto(String name , @JsonProperty("html_url") String htmlUrl , List<BranchDto> branches) {
}
