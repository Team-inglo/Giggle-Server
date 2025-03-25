package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.repository.AdditionalLanguageRepository;
import com.inglo.giggle.resume.repository.mysql.AdditionalLanguageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdditionalLanguageRepositoryImpl implements AdditionalLanguageRepository {

    private final AdditionalLanguageJpaRepository additionalLanguageJpaRepository;

    @Override
    public AdditionalLanguage findWithLanguageSkillByIdOrElseThrow(Long additionalLanguageSkillId) {
        return additionalLanguageJpaRepository.findById(additionalLanguageSkillId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ADDITIONAL_LANGUAGE));
    }

    @Override
    public void save(AdditionalLanguage additionalLanguage) {
        additionalLanguageJpaRepository.save(additionalLanguage);
    }

    @Override
    public void delete(AdditionalLanguage additionalLanguage) {
        additionalLanguageJpaRepository.delete(additionalLanguage);
    }

}
