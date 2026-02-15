package com.company.githubsearcher.dto;

import lombok.Data;
import java.util.List;

@Data
public class GitHubApiResponse {

    private int total_count;
    private List<GitHubRepoItem> items;
}

