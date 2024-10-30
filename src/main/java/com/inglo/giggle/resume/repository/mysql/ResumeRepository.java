package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.Resume;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID>{
  
    @EntityGraph(attributePaths = {"workExperiences"})
    Optional<Resume> findWithWorkExperiencesAndLanguageSkillByAccountId(UUID id);

    @EntityGraph(attributePaths = {"languageSkill"})
    Optional<Resume> findWithLanguageSkillByAccountId(UUID id);

    @EntityGraph(attributePaths = {"educations", "languageSkill"})
    Optional<Resume> findWithEducationsAndLanguageSkillByAccountId(@Param(value = "accountId") UUID accountId);

}
