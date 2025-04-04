package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.persistence.mapper.ResumeMapper;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.ResumeJpaRepository;
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
        return ResumeMapper.toDomain(resumeJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME)));
    }

    @Override
    public Resume findWithEducationsAndLanguageSkillByAccountIdOrElseThrow(UUID accountId) {
        return ResumeMapper.toDomain(resumeJpaRepository.findWithEducationsAndLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME)));
    }

    @Override
    public Optional<Resume> findWithEducationsAndLanguageSkillByAccountIdOptional(UUID accountId) {
        return resumeJpaRepository.findWithEducationsAndLanguageSkillByAccountId(accountId)
                .map(ResumeMapper::toDomain);
    }

    @Override
    public Resume findByAccountIdOrElseThrow(UUID accountId) {
        return ResumeMapper.toDomain(resumeJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME)));
    }

    @Override
    public Resume save(Resume resume) {
        ResumeEntity resumeEntity = ResumeMapper.toEntity(resume);
        return ResumeMapper.toDomain(resumeJpaRepository.save(resumeEntity));
    }
}
