package com.example.ApiTask.serviceTest;

import com.example.ApiTask.dtos.BranchDto;
import com.example.ApiTask.dtos.CommitDto;
import com.example.ApiTask.dtos.RepositoryDto;
import com.example.ApiTask.service.GitService;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;




public class GitServiceTest {

    /**
     * gitService - is instance of GitService which is using in test.
     * */
    private GitService gitService;
    /**
     * restTemplateMock - is mock of RestTemplate which is using to simulate github Api.
     * */
    private RestTemplate restTemplateMock;


    /**
     * RepositoryDto[] is array RepositoryDto for test user.
     * */
    private final RepositoryDto[] repositoryList ={
            new RepositoryDto("TestName1","TestUrl1",null),
            new RepositoryDto("TestName2","TestUrl2",null),
            new RepositoryDto("TestName3","TestUrl3",null)
    };
    /**
     * BranchesDto[] is array BranchesDto for test user repositories.
     * */
    private final BranchDto[] branchListDto={
            new BranchDto("TestBranch1",new CommitDto("testSha1")),
            new BranchDto("TestBranch2",new CommitDto("testSha2")),
            new BranchDto("TestBranch3",new CommitDto("testSha3"))
    };

    /**
     * userName - is name of test user.
     * */
    private final String userName="TestUser1";

    /**
     *  getRepositoryTest - This is a method to validate the working of Git Service
     * */
    @Test
    public void  getRepositoryTest(){

        restTemplateMock = Mockito.mock(RestTemplate.class);
        ResponseEntity<BranchDto[]> successfulResponseBranch = ResponseEntity.ok(branchListDto);
        ResponseEntity<BranchDto[]> errorResponseBranch = ResponseEntity.notFound().build();
        ResponseEntity<RepositoryDto[]> successfulResponseRepository = ResponseEntity.ok(repositoryList);
        ResponseEntity<RepositoryDto[]> errorResponseRepository  = ResponseEntity.notFound().build();
        Map<String, String> params = new HashMap<String, String>();
        params.put("type","owner");


        when(restTemplateMock.exchange(anyString(),eq(HttpMethod.GET), eq(null), eq(BranchDto[].class)))
                .thenReturn(successfulResponseBranch)
                .thenReturn(errorResponseBranch)
                .thenReturn(successfulResponseBranch);

        when(restTemplateMock.exchange(anyString(),eq(HttpMethod.GET), eq(null), eq(RepositoryDto[].class),eq(params)))
                .thenReturn(successfulResponseRepository)
                .thenReturn(errorResponseRepository)
                .thenReturn(successfulResponseRepository);

        gitService= new GitService();
        gitService.setRestTemplate(restTemplateMock);


        Optional<List<RepositoryDto>> result = gitService.getRepository(userName) ;
        assertTrue("Result should be present",result.isPresent());
        assertFalse("List of repository should not be empty",result.get().isEmpty());
        List<BranchDto> branchList = result.get().get(0).getBranches();
        assertFalse("The branch list for the first repository should contain items",branchList.isEmpty());
        branchList = result.get().get(1).getBranches();
        assertNull("The branch list for the second repository should be null",branchList);


        verify(restTemplateMock, times(3)).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(BranchDto[].class));
        verify(restTemplateMock, times(1)).exchange(anyString(), eq(HttpMethod.GET),eq(null), eq(RepositoryDto[].class),eq(params));

    }
}
