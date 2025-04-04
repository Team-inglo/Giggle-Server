package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.entity.WorkExperienceEntity;
import com.inglo.giggle.resume.persistence.mapper.WorkExperienceMapper;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.WorkExperienceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WorkExperienceRepositoryImpl implements WorkExperienceRepository {

    private final WorkExperienceJpaRepository workExperienceJpaRepository;

    @Override
    public WorkExperience findByIdOrElseThrow(Long workExperienceId) {
        return WorkExperienceMapper.toDomain(workExperienceJpaRepository.findById(workExperienceId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_WORK_EXPERIENCE)));
    }

    @Override
    public List<WorkExperience> findAllByResumeId(UUID resumeId) {
        return WorkExperienceMapper.toDomains(workExperienceJpaRepository.findAllByResumeId(resumeId));
    }

    @Override
    public WorkExperience save(WorkExperience workExperience) {
        WorkExperienceEntity entity = workExperienceJpaRepository.save(WorkExperienceMapper.toEntity(workExperience));
        return WorkExperienceMapper.toDomain(entity);
    }

    @Override
    public void saveAll(List<WorkExperience> workExperiences) {
        List<WorkExperienceEntity> entities = WorkExperienceMapper.toEntities(workExperiences);
        workExperienceJpaRepository.saveAll(entities);
    }

    @Override
    public void delete(WorkExperience workExperience) {
        workExperienceJpaRepository.delete(WorkExperienceMapper.toEntity(workExperience));
    }
}
