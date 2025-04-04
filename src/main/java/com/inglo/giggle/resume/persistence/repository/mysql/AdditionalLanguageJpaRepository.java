package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.resume.persistence.entity.AdditionalLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdditionalLanguageJpaRepository extends JpaRepository<AdditionalLanguageEntity, Long> {
    List<AdditionalLanguageEntity> findAllByLanguageSkillsId(UUID languageSkillId);
}
