package com.example.ApiTaks.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryDto {
    private  String name;
    private  String html_url;
    private List<BranchDto> branches;

}
