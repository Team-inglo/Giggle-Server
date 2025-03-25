package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeJpaRepository resumeJpaRepository;

    @Override
    public Resume findByIdOrElseThrow(UUID id) {
        return resumeJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Resume findWithWorkExperiencesAndLanguageSkillByAccountIdOrElseThrow(UUID id) {
        return resumeJpaRepository.findWithWorkExperiencesAndLanguageSkillByAccountId(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Resume findWithLanguageSkillByAccountIdOrElseThrow(UUID id) {
        return resumeJpaRepository.findWithLanguageSkillByAccountId(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Resume findWithEducationsAndLanguageSkillByAccountIdOrElseThrow(UUID accountId) {
        return resumeJpaRepository.findWithEducationsAndLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Optional<Resume> findWithEducationsAndLanguageSkillByAccountIdOptional(UUID accountId) {
        return resumeJpaRepository.findWithEducationsAndLanguageSkillByAccountId(accountId);
    }

    @Override
    public Resume findWithEducationsByAccountIdOrElseThrow(UUID accountId) {
        return resumeJpaRepository.findWithEducationsByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public void save(Resume resume) {
        resumeJpaRepository.save(resume);
    }

    @Override
    public void delete(Resume resume) {
        resumeJpaRepository.delete(resume);
    }
}
