package com.company.githubsearcher.service;

import com.company.githubsearcher.dto.GitHubApiResponse;
import com.company.githubsearcher.dto.GitHubRepoItem;
import com.company.githubsearcher.dto.GitHubSearchRequest;
import com.company.githubsearcher.entity.GitHubRepositoryEntity;
import com.company.githubsearcher.repository.GitHubRepositoryEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GitHubSearchService {

    private final GitHubApiClient gitHubApiClient;
    private final GitHubRepositoryEntityRepository repository;

    public GitHubSearchService(GitHubApiClient gitHubApiClient,
                               GitHubRepositoryEntityRepository repository) {
        this.gitHubApiClient = gitHubApiClient;
        this.repository = repository;
    }

    @Transactional
    public List<GitHubRepositoryEntity> searchAndSave(GitHubSearchRequest request) {

        GitHubApiResponse apiResponse =
                gitHubApiClient.searchRepositories(request);

        if (apiResponse == null || apiResponse.getItems() == null) {
            return List.of();
        }

        return apiResponse.getItems()
                .stream()
                .map(this::upsertRepository)
                .toList();
    }

    private GitHubRepositoryEntity upsertRepository(GitHubRepoItem item) {

        GitHubRepositoryEntity entity =
                repository.findById(item.getId())
                        .orElse(new GitHubRepositoryEntity());

        entity.setId(item.getId());
        entity.setName(item.getName());
        entity.setDescription(item.getDescription());
        entity.setOwner(item.getOwner().getLogin());
        entity.setLanguage(item.getLanguage());
        entity.setStars(item.getStargazersCount());
        entity.setForks(item.getForksCount());
        entity.setLastUpdated(item.getUpdatedAt());

        return repository.save(entity);
    }
}
