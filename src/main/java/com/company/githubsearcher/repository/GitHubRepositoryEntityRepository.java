package com.company.githubsearcher.repository;

import com.company.githubsearcher.entity.GitHubRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitHubRepositoryEntityRepository
        extends JpaRepository<GitHubRepositoryEntity, Long> {
}
