package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.resume.persistence.entity.AdditionalLanguageEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdditionalLanguageJpaRepository extends JpaRepository<AdditionalLanguageEntity, Long> {
    @EntityGraph(attributePaths = {"languageSkillEntity"})
    Optional<AdditionalLanguageEntity> findWithLanguageSkillById(Long additionalLanguageSkillId);
}
