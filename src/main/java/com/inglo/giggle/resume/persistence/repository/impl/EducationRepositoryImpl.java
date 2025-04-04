package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.persistence.entity.EducationEntity;
import com.inglo.giggle.resume.persistence.mapper.EducationMapper;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.EducationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EducationRepositoryImpl implements EducationRepository {

    private final EducationJpaRepository educationJpaRepository;

    @Override
    public Education findByIdOrElseThrow(Long id) {
        return EducationMapper.toDomain(educationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EDUCATION)));
    }

    @Override
    public List<Education> findEducationByAccountIdAndEducationLevel(UUID userId, EEducationLevel educationLevel) {
        return EducationMapper.toDomains(educationJpaRepository.findEducationByAccountIdAndEducationLevel(userId, educationLevel));
    }

    public List<Education> findAllByResumeId(UUID resumeId) {
        return EducationMapper.toDomains(educationJpaRepository.findAllByResumeId(resumeId));
    }

    @Override
    public Education save(Education education) {
        EducationEntity entity = educationJpaRepository.save(EducationMapper.toEntity(education));
        return EducationMapper.toDomain(entity);
    }

    @Override
    public void saveAll(List<Education> educations) {
        List<EducationEntity> entities = EducationMapper.toEntities(educations);
        educationJpaRepository.saveAll(entities);
    }

    @Override
    public void delete(Education education) {
        educationJpaRepository.delete(EducationMapper.toEntity(education));
    }
}
