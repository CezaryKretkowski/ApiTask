package com.example.ApiTask.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * CommitDto -  is a data transfer object to store information about commit.
 * */
@Getter
@Setter
public class CommitDto {
    private String sha;
}
