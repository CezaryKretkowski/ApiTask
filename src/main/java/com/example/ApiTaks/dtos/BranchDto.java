package com.example.ApiTaks.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDto {
    private String name;
    private CommitDto commit;

}
