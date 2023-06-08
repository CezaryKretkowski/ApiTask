package com.example.ApiTask.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CommitDto -  is a data transfer object to store information about commit.
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommitDto {
    private String sha;
}
