package com.company.githubsearcher.repository;

import com.company.githubsearcher.entity.GitHubRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GitHubRepositoryEntityRepository extends JpaRepository<GitHubRepositoryEntity, Long> {

    @Query("""
        SELECT r FROM GitHubRepositoryEntity r
        WHERE (:language IS NULL OR r.language = :language)
          AND (:minStars IS NULL OR r.stars >= :minStars)
        ORDER BY
          CASE WHEN :sort = 'forks' THEN r.forks END DESC,
          CASE WHEN :sort = 'updated' THEN r.lastUpdated END DESC,
          CASE WHEN :sort = 'stars' THEN r.stars END DESC
    """)
    List<GitHubRepositoryEntity> findFiltered(
            @Param("language") String language,
            @Param("minStars") Integer minStars,
            @Param("sort") String sort
    );
}
