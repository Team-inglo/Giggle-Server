package com.inglo.giggle.resume.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.resume.adapter.out.persistence.entity.ResumeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResumeJpaRepository extends JpaRepository<ResumeEntity, UUID>{

    @EntityGraph(attributePaths = {"educationEntities"})
    Optional<ResumeEntity> findWithEducationsByAccountId(UUID accountId);

    @EntityGraph(attributePaths = {"workExperienceEntities"})
    Optional<ResumeEntity> findWithWorkExperiencesByAccountId(UUID accountId);

    @EntityGraph(attributePaths = {"languageSkillEntity"})
    Optional<ResumeEntity> findWithLanguageSkillByAccountId(UUID accountId);

    @EntityGraph(attributePaths = {"languageSkillEntity", "languageSkillEntity.additionalLanguageEntities"})
    Optional<ResumeEntity> findWithLanguageSkillAndAdditionalLanguageByAccountId(UUID accountId);

    @EntityGraph(attributePaths = {"languageSkillEntity", "educationEntities"})
    Optional<ResumeEntity> findWithLanguageSkillAndEducationsByAccountId(UUID accountId);

}
