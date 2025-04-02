package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResumeJpaRepository extends JpaRepository<ResumeEntity, UUID>{
  
    @EntityGraph(attributePaths = {"workExperienceEntities"})
    Optional<ResumeEntity> findWithWorkExperiencesAndLanguageSkillByAccountId(UUID id);

    @EntityGraph(attributePaths = {"languageSkillEntity"})
    Optional<ResumeEntity> findWithLanguageSkillByAccountId(UUID id);

    @EntityGraph(attributePaths = {"educationEntities", "languageSkillEntity"})
    Optional<ResumeEntity> findWithEducationsAndLanguageSkillByAccountId(@Param(value = "accountId") UUID accountId);

    @EntityGraph(attributePaths = {"educationEntities"})
    Optional<ResumeEntity> findWithEducationsByAccountId(UUID accountId);
}
