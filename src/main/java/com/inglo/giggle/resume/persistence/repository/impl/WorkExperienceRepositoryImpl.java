package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.persistence.entity.WorkExperienceEntity;
import com.inglo.giggle.resume.persistence.mapper.ResumeMapper;
import com.inglo.giggle.resume.persistence.mapper.WorkExperienceMapper;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.ResumeJpaRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.WorkExperienceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkExperienceRepositoryImpl implements WorkExperienceRepository {

    private final WorkExperienceJpaRepository workExperienceJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;

    @Override
    public WorkExperience findByIdOrElseThrow(Long workExperienceId) {
        return WorkExperienceMapper.toDomain(workExperienceJpaRepository.findById(workExperienceId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_WORK_EXPERIENCE)));
    }

    @Override
    public List<WorkExperience> findAllByResume(Resume resume) {
        return WorkExperienceMapper.toDomains(workExperienceJpaRepository.findAllByResumeEntity(ResumeMapper.toEntity(resume)));
    }

    @Override
    public WorkExperience save(WorkExperience workExperience) {
        WorkExperienceEntity entity = workExperienceJpaRepository.save(WorkExperienceMapper.toEntity(workExperience));
        Optional<ResumeEntity> resumeEntity = resumeJpaRepository.findById(entity.getResumeEntity().getAccountId());
        if (resumeEntity.isPresent()) {
            entity.fetchResumeEntity(resumeEntity.get());
            workExperienceJpaRepository.save(entity);
        }
        return WorkExperienceMapper.toDomain(entity);
    }

    @Override
    public void delete(WorkExperience workExperience) {
        workExperienceJpaRepository.delete(WorkExperienceMapper.toEntity(workExperience));
    }
}
