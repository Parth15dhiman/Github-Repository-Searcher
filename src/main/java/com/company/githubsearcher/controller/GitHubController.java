package com.company.githubsearcher.controller;

import com.company.githubsearcher.dto.GitHubSearchRequest;
import com.company.githubsearcher.entity.GitHubRepositoryEntity;
import com.company.githubsearcher.service.GitHubSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubSearchService gitHubSearchService;

    public GitHubController(GitHubSearchService gitHubSearchService) {
        this.gitHubSearchService = gitHubSearchService;
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchRepositories(
            @RequestBody GitHubSearchRequest request) {

        List<GitHubRepositoryEntity> repositories =
                gitHubSearchService.searchAndSave(request);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Repositories fetched and saved successfully",
                        "repositories", repositories
                )
        );
    }

    @GetMapping("/repositories")
    public ResponseEntity<?> getRepositories(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minStars,
            @RequestParam(defaultValue = "stars") String sort) {

        return ResponseEntity.ok(
                Map.of(
                        "repositories",
                        gitHubSearchService.getRepositories(language, minStars, sort)
                )
        );
    }

}
