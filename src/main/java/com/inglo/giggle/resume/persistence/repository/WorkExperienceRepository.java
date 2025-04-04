package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;

import java.util.List;
import java.util.UUID;

public interface WorkExperienceRepository {

    WorkExperience findByIdOrElseThrow(Long id);

    List<WorkExperience> findAllByResumeId(UUID resumeId);

    WorkExperience save(WorkExperience workExperience);

    void saveAll(List<WorkExperience> workExperiences);

    void delete(WorkExperience workExperience);
}
