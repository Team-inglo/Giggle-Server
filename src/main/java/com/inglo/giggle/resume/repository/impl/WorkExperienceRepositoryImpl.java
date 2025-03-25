package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.repository.mysql.WorkExperienceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkExperienceRepositoryImpl implements WorkExperienceRepository {

    private final WorkExperienceJpaRepository workExperienceJpaRepository;

    @Override
    public WorkExperience findByIdOrElseThrow(Long workExperienceId) {
        return workExperienceJpaRepository.findById(workExperienceId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_WORK_EXPERIENCE));
    }

    @Override
    public List<WorkExperience> findAllByResume(Resume resume) {
        return workExperienceJpaRepository.findAllByResume(resume);
    }

    @Override
    public void save(WorkExperience workExperience) {
        workExperienceJpaRepository.save(workExperience);
    }

    @Override
    public void delete(WorkExperience workExperience) {
        workExperienceJpaRepository.delete(workExperience);
    }
}
