package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.persistence.entity.AdditionalLanguageEntity;
import com.inglo.giggle.resume.persistence.entity.LanguageSkillEntity;
import com.inglo.giggle.resume.persistence.mapper.AdditionalLanguageMapper;
import com.inglo.giggle.resume.persistence.repository.AdditionalLanguageRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.AdditionalLanguageJpaRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.LanguageSkillJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdditionalLanguageRepositoryImpl implements AdditionalLanguageRepository {

    private final AdditionalLanguageJpaRepository additionalLanguageJpaRepository;
    private final LanguageSkillJpaRepository languageSkillJpaRepository;

    @Override
    public AdditionalLanguage findWithLanguageSkillByIdOrElseThrow(Long additionalLanguageSkillId) {
        return AdditionalLanguageMapper.toDomain(additionalLanguageJpaRepository.findById(additionalLanguageSkillId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ADDITIONAL_LANGUAGE)));
    }

    @Override
    public AdditionalLanguage save(AdditionalLanguage additionalLanguage) {
        AdditionalLanguageEntity entity = additionalLanguageJpaRepository.save(AdditionalLanguageMapper.toEntity(additionalLanguage));
        Optional<LanguageSkillEntity> languageSkillEntity = languageSkillJpaRepository.findById(additionalLanguage.getLanguageSkillId());
        if (languageSkillEntity.isPresent()) {
            entity.fetchLanguageSkillEntity(languageSkillEntity.get());
            additionalLanguageJpaRepository.save(entity);
        }
        return AdditionalLanguageMapper.toDomain(entity);
    }

    @Override
    public void delete(AdditionalLanguage additionalLanguage) {
        additionalLanguageJpaRepository.delete(AdditionalLanguageMapper.toEntity(additionalLanguage));
    }

}
