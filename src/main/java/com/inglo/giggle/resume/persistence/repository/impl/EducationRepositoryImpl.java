package com.inglo.giggle.resume.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.entity.EducationEntity;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.persistence.mapper.EducationMapper;
import com.inglo.giggle.resume.persistence.mapper.ResumeMapper;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.EducationJpaRepository;
import com.inglo.giggle.resume.persistence.repository.mysql.ResumeJpaRepository;
import com.inglo.giggle.school.persistence.entity.SchoolEntity;
import com.inglo.giggle.school.persistence.repository.mysql.SchoolJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EducationRepositoryImpl implements EducationRepository {

    private final EducationJpaRepository educationJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final SchoolJpaRepository schoolJpaRepository;

    @Override
    public Education findByIdOrElseThrow(Long id) {
        return EducationMapper.toDomain(educationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EDUCATION)));
    }

    @Override
    public List<Education> findEducationByAccountIdAndEducationLevel(UUID userId, EEducationLevel educationLevel) {
        return EducationMapper.toDomains(educationJpaRepository.findEducationByAccountIdAndEducationLevel(userId, educationLevel));
    }

    @Override
    public List<Education> findAllByResume(Resume resume) {
        return EducationMapper.toDomains(educationJpaRepository.findAllByResumeEntity(ResumeMapper.toEntity(resume)));
    }

    @Override
    public Education findWithSchoolByIdOrElseThrow(Long id) {
        return EducationMapper.toDomain(educationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EDUCATION)));
    }

    @Override
    public Education save(Education education) {
        EducationEntity entity = educationJpaRepository.save(EducationMapper.toEntity(education));
        Optional<SchoolEntity> schoolEntity = schoolJpaRepository.findById(education.getSchoolId());
        if (schoolEntity.isPresent()) {
            entity.fetchSchoolEntity(schoolEntity.get());
            educationJpaRepository.save(entity);
        }
        Optional<ResumeEntity> resumeEntity = resumeJpaRepository.findById(education.getResumeId());
        if (resumeEntity.isPresent()) {
            entity.fetchResumeEntity(resumeEntity.get());
            educationJpaRepository.save(entity);
        }
        return EducationMapper.toDomain(entity);
    }

    @Override
    public void delete(Education education) {
        educationJpaRepository.delete(EducationMapper.toEntity(education));
    }
}
