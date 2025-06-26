package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.type.EAdditionalLanguageLevelType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdditionalLanguageService {
    public AdditionalLanguage createAdditionalLanguage(
            String language,
            EAdditionalLanguageLevelType level,
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
            EAdditionalLanguageLevelType additionalLanguageLevelType
    ) {
        additionalLanguage.updateLanguageName(language);
        additionalLanguage.updateLevel(additionalLanguageLevelType);
        return additionalLanguage;
    }

    public void checkAdditionalLanguageValidation(AdditionalLanguage additionalLanguage, UUID accountId) {
        if (!additionalLanguage.getLanguageSkill().getResume().getAccountId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void checkIsExistAdditionalLanguage(
            List<AdditionalLanguage> additionalLanguages,
            String language
    ) {
        if (additionalLanguages.stream().
                anyMatch(
                        additionalLanguage -> additionalLanguage.getLanguageName().equals(language)
                )
        ) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }
    }
}
