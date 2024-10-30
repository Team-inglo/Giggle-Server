package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.LanguageSkill;
import org.springframework.stereotype.Service;

@Service
public class AdditionalLanguageService {
    public AdditionalLanguage createAdditionalLanguage(
            String language,
            Integer level,
            LanguageSkill languageSkill
    ) {
        return AdditionalLanguage.builder()
                .languageName(language)
                .level(level)
                .languageSkill(languageSkill)
                .build();
    }

    public AdditionalLanguage updateAdditionalLanguage(
            AdditionalLanguage additionalLanguage,
            String language,
            Integer level
    ) {
        additionalLanguage.updateLanguageName(language);
        additionalLanguage.updateLevel(level);
        return additionalLanguage;
    }
}
