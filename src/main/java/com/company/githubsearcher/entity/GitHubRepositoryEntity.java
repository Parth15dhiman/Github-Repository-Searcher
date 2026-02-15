package com.company.githubsearcher.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "github_repository")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepositoryEntity {

    @Id
    private Long id;

    private String name;

    @Column(length = 5000)
    private String description;

    private String owner;

    private String language;

    private Integer stars;

    private Integer forks;

    private Instant lastUpdated;
}
