package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface LanguageSkillRepository extends JpaRepository<LanguageSkill, UUID>{
    @EntityGraph(attributePaths = {"additionalLanguages"})
    Optional<LanguageSkill> findByResume(Resume resume);
}
