package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.entity.LanguageSkillEntity;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.persistence.mapper.LanguageSkillMapper;
import com.inglo.giggle.resume.persistence.mapper.ResumeMapper;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.LanguageSkillJpaRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.ResumeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LanguageSkillRepositoryImpl implements LanguageSkillRepository {

    private final LanguageSkillJpaRepository languageSkillJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;

    @Override
    public LanguageSkill findByIdOrElseThrow(UUID id) {
        return LanguageSkillMapper.toDomain(languageSkillJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL)));
    }

    @Override
    public LanguageSkill findByResumeOrElseThrow(Resume resume) {
        return LanguageSkillMapper.toDomain(languageSkillJpaRepository.findByResumeEntity(ResumeMapper.toEntity(resume))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL)));

    }

    @Override
    public LanguageSkill findByResumeIdOrElseThrow(UUID resumeId) {
        return LanguageSkillMapper.toDomain(languageSkillJpaRepository.findByResumeId(resumeId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL)));
    }

    @Override
    public LanguageSkill save(LanguageSkill languageSkill) {
        ResumeEntity resumeEntity = resumeJpaRepository.findById(languageSkill.getResumeId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
        LanguageSkillEntity entity = LanguageSkillMapper.toEntity(languageSkill);
        entity.fetchResumeEntity(resumeEntity);
        entity = languageSkillJpaRepository.save(entity);
        return LanguageSkillMapper.toDomain(entity);
    }
}
