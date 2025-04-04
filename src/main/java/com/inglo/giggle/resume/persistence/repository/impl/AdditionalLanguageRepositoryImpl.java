package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.persistence.entity.AdditionalLanguageEntity;
import com.inglo.giggle.resume.persistence.mapper.AdditionalLanguageMapper;
import com.inglo.giggle.resume.persistence.repository.AdditionalLanguageRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.AdditionalLanguageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AdditionalLanguageRepositoryImpl implements AdditionalLanguageRepository {

    private final AdditionalLanguageJpaRepository additionalLanguageJpaRepository;

    @Override
    public List<AdditionalLanguage> findAllByLanguageSkillId(UUID resumeId) {
        return AdditionalLanguageMapper.toDomains(additionalLanguageJpaRepository.findAllByLanguageSkillsId(resumeId));
    }

    @Override
    public AdditionalLanguage save(AdditionalLanguage additionalLanguage) {
        AdditionalLanguageEntity entity = additionalLanguageJpaRepository.save(AdditionalLanguageMapper.toEntity(additionalLanguage));
        return AdditionalLanguageMapper.toDomain(entity);
    }

    @Override
    public void saveAll(List<AdditionalLanguage> additionalLanguages) {
        List<AdditionalLanguageEntity> entities = AdditionalLanguageMapper.toEntities(additionalLanguages);
        additionalLanguageJpaRepository.saveAll(entities);
    }

    @Override
    public void delete(AdditionalLanguage additionalLanguage) {
        additionalLanguageJpaRepository.delete(AdditionalLanguageMapper.toEntity(additionalLanguage));
    }

}
