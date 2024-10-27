package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.Resume;
import io.lettuce.core.dynamic.annotation.Param;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID>{

    @NotNull
    @EntityGraph(attributePaths = {"educations", "users", "languages", "skills"})
    Optional<Resume> findById(@NotNull @Param(value = "accountId") UUID accountId);
}
