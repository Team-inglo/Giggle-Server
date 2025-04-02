package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.resume.domain.AdditionalLanguage;

public interface AdditionalLanguageRepository {
    AdditionalLanguage findWithLanguageSkillByIdOrElseThrow(Long additionalLanguageSkillId);

    AdditionalLanguage save(AdditionalLanguage additionalLanguage);

    void delete(AdditionalLanguage additionalLanguage);
}
