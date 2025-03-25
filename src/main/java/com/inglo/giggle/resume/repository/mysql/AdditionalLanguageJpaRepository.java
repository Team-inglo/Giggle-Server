package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.AdditionalLanguage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdditionalLanguageJpaRepository extends JpaRepository<AdditionalLanguage, Long> {
    @EntityGraph(attributePaths = {"languageSkill"})
    Optional<AdditionalLanguage> findWithLanguageSkillById(Long additionalLanguageSkillId);
}
