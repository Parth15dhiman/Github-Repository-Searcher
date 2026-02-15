package com.company.githubsearcher.service;

import com.company.githubsearcher.dto.GitHubApiResponse;
import com.company.githubsearcher.dto.GitHubSearchRequest;
import com.company.githubsearcher.exception.GitHubApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class GitHubApiClient {

    private static final String GITHUB_SEARCH_URL = "https://api.github.com/search/repositories";

    private final RestTemplate restTemplate;

    @Value("${github.token:}")
    private String githubToken;

    public GitHubApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GitHubApiResponse searchRepositories(GitHubSearchRequest request) {

        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            URI uri = buildUri(request);

            ResponseEntity<GitHubApiResponse> response =
                    restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            entity,
                            GitHubApiResponse.class
                    );

            return response.getBody();

        } catch (HttpClientErrorException e) {
            throw new GitHubApiException(
                    "GitHub API error: " + e.getStatusCode()
            );
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("User-Agent", "github-searcher-app");

        if (!githubToken.isBlank()) {
            headers.setBearerAuth(githubToken);
        }

        return headers;
    }

    private URI buildUri(GitHubSearchRequest request) {

        StringBuilder q = new StringBuilder(request.getQuery());

        if (request.getLanguage() != null) {
            q.append("+language:").append(request.getLanguage());
        }

        String sort = request.getSort() != null
                ? request.getSort()
                : "stars";

        return URI.create(
                GITHUB_SEARCH_URL +
                        "?q=" + q +
                        "&sort=" + sort +
                        "&order=desc"
        );
    }
}
