package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LanguageSkillRepositoryImpl implements LanguageSkillRepository {

    private final LanguageSkillJpaRepository languageSkillJpaRepository;

    @Override
    public LanguageSkill findByIdOrElseThrow(UUID id) {
        return languageSkillJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL));
    }

    @Override
    public LanguageSkill findByResumeOrElseThrow(Resume resume) {
        return languageSkillJpaRepository.findByResume(resume)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL));

    }

    @Override
    public LanguageSkill findByResumeIdOrElseThrow(UUID resumeId) {
        return languageSkillJpaRepository.findByResumeId(resumeId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL));
    }

    @Override
    public void save(LanguageSkill languageSkill) {
        languageSkillJpaRepository.save(languageSkill);
    }
}
