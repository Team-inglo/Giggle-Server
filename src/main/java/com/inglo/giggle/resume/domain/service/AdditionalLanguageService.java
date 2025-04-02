package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalLanguageService {

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
