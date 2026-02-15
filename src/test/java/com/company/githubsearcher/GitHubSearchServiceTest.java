package com.company.githubsearcher;

import com.company.githubsearcher.dto.GitHubApiResponse;
import com.company.githubsearcher.dto.GitHubRepoItem;
import com.company.githubsearcher.dto.GitHubRepoOwner;
import com.company.githubsearcher.dto.GitHubSearchRequest;
import com.company.githubsearcher.entity.GitHubRepositoryEntity;
import com.company.githubsearcher.service.GitHubApiClient;
import com.company.githubsearcher.service.GitHubSearchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class GitHubSearchServiceTest {

    @MockitoBean
    private GitHubApiClient gitHubApiClient;


    @Autowired
    private GitHubSearchService service;

    @Test
    void shouldSaveRepositoriesFromGitHub() {
        GitHubApiResponse response = new GitHubApiResponse();
        response.setItems(List.of(mockItem()));

        Mockito.when(gitHubApiClient.searchRepositories(Mockito.any()))
                .thenReturn(response);

        List<GitHubRepositoryEntity> saved =
                service.searchAndSave(new GitHubSearchRequest("spring", "Java", "stars"));

        assertFalse(saved.isEmpty());
    }

    private GitHubRepoItem mockItem() {
        GitHubRepoItem item = new GitHubRepoItem();

        item.setId(1L);
        item.setName("spring-framework");
        item.setDescription("Spring Framework");
        item.setLanguage("Java");
        item.setStargazersCount(50000);
        item.setForksCount(10000);

        GitHubRepoOwner owner = new GitHubRepoOwner();
        owner.setLogin("spring-projects");
        item.setOwner(owner);

        item.setUpdatedAt(Instant.now());

        return item;
    }

}

