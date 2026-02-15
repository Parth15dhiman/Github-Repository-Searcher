package com.company.githubsearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class GitHubRepoItem {

    private Long id;
    private String name;
    private String description;
    private String language;

    @JsonProperty("stargazers_count")
    private Integer stargazersCount;

    @JsonProperty("forks_count")
    private Integer forksCount;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    private GitHubRepoOwner owner;
}
