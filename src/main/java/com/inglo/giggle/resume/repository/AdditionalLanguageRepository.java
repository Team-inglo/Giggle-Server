package com.inglo.giggle.resume.repository;

import com.inglo.giggle.resume.domain.AdditionalLanguage;

public interface AdditionalLanguageRepository {
    AdditionalLanguage findWithLanguageSkillByIdOrElseThrow(Long additionalLanguageSkillId);

    void save(AdditionalLanguage additionalLanguage);

    void delete(AdditionalLanguage additionalLanguage);
}
