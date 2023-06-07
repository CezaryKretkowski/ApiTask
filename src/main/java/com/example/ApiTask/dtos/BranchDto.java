package com.example.ApiTask.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * BranchDto -  is a data transfer object to store information about branch.
 * */
@Getter
@Setter
public class BranchDto {
    private String name;
    private CommitDto commit;

}
