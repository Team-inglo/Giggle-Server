package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.EducationRepository;
import com.inglo.giggle.resume.repository.mysql.EducationJpaRepository;
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
        return educationJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EDUCATION));
    }

    @Override
    public List<Education> findEducationByAccountIdAndEducationLevel(UUID userId, EEducationLevel educationLevel) {
        return educationJpaRepository.findEducationByAccountIdAndEducationLevel(userId, educationLevel);
    }

    @Override
    public List<Education> findAllByResume(Resume resume) {
        return educationJpaRepository.findAllByResume(resume);
    }

    @Override
    public Education findWithSchoolByIdOrElseThrow(Long id) {
        return educationJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EDUCATION));
    }

    @Override
    public void save(Education education) {
        educationJpaRepository.save(education);
    }

    @Override
    public void delete(Education education) {
        educationJpaRepository.delete(education);
    }
}
