package com.example.ApiTask.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BranchDto -  is a data transfer object to store information about branch.
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {
    private String name;
    private CommitDto commit;

}
