package com.company.githubsearcher;

import com.company.githubsearcher.entity.GitHubRepositoryEntity;
import com.company.githubsearcher.repository.GitHubRepositoryEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class GitHubRepositoryEntityRepositoryTest {

    @Autowired
    private GitHubRepositoryEntityRepository repository;

    @Test
    void shouldFilterByLanguageAndStars() {
        GitHubRepositoryEntity repo = new GitHubRepositoryEntity(
                1L, "test", "desc", "owner",
                "Java", 200, 10, Instant.now()
        );

        repository.save(repo);

        List<GitHubRepositoryEntity> result =
                repository.findFiltered("Java", 100, "stars");

        assertEquals(1, result.size());
    }
}

