package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.resume.domain.AdditionalLanguage;

import java.util.List;
import java.util.UUID;

public interface AdditionalLanguageRepository {
    List<AdditionalLanguage> findAllByLanguageSkillId(UUID resumeId);

    AdditionalLanguage save(AdditionalLanguage additionalLanguage);

    void saveAll(List<AdditionalLanguage> additionalLanguages);

    void delete(AdditionalLanguage additionalLanguage);
}
