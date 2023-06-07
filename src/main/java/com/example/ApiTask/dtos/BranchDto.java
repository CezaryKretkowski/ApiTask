package com.example.ApiTask.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDto {
    private String name;
    private CommitDto commit;

}
