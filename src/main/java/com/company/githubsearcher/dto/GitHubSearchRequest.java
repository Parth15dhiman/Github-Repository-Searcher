package com.company.githubsearcher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GitHubSearchRequest {
    @NotBlank
    private String query;
    private String language;
    private String sort;
}
