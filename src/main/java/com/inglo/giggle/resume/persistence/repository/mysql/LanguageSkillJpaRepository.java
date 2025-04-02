package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.resume.persistence.entity.LanguageSkillEntity;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LanguageSkillJpaRepository extends JpaRepository<LanguageSkillEntity, UUID>{
    @EntityGraph(attributePaths = {"additionalLanguageEntities"})
    Optional<LanguageSkillEntity> findByResumeEntity(ResumeEntity resumeEntity);

    @EntityGraph(attributePaths = {"additionalLanguageEntities"})
    Optional<LanguageSkillEntity> findByResumeId(UUID resumeId);
}
