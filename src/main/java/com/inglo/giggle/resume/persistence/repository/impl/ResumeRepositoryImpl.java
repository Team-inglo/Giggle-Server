package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.account.persistence.entity.UserEntity;
import com.inglo.giggle.account.persistence.repository.mysql.UserJpaRepository;
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
    private final UserJpaRepository userJpaRepository;

    @Override
    public Resume findByIdOrElseThrow(UUID id) {
        return ResumeMapper.toDomain(resumeJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME)));
    }

    @Override
    public Resume findWithWorkExperiencesAndLanguageSkillByAccountIdOrElseThrow(UUID id) {
        return ResumeMapper.toDomain(resumeJpaRepository.findWithWorkExperiencesAndLanguageSkillByAccountId(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME)));
    }

    @Override
    public Resume findWithLanguageSkillByAccountIdOrElseThrow(UUID id) {
        return ResumeMapper.toDomain(resumeJpaRepository.findWithLanguageSkillByAccountId(id)
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
    public Resume findWithEducationsByAccountIdOrElseThrow(UUID accountId) {
        return ResumeMapper.toDomain(resumeJpaRepository.findWithEducationsByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME)));
    }

    @Override
    public Resume save(Resume resume) {
        UserEntity userEntity = userJpaRepository.findById(resume.getAccountId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        ResumeEntity entity = ResumeMapper.toEntity(resume);
        entity.fetchUserEntity(userEntity);
        entity = resumeJpaRepository.save(entity);
        return ResumeMapper.toDomain(entity);
    }
}
