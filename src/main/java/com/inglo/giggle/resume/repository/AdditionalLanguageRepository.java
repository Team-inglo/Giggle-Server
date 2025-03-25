package com.inglo.giggle.resume.repository;

import com.inglo.giggle.resume.domain.AdditionalLanguage;

import java.util.Optional;

public interface AdditionalLanguageRepository {
    AdditionalLanguage findWithLanguageSkillByIdOrElseThrow(Long additionalLanguageSkillId);

    void save(AdditionalLanguage additionalLanguage);

    void delete(AdditionalLanguage additionalLanguage);
}
