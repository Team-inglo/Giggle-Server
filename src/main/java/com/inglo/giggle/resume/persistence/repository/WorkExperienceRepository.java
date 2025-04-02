package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;

import java.util.List;

public interface WorkExperienceRepository {

    WorkExperience findByIdOrElseThrow(Long id);

    List<WorkExperience> findAllByResume(Resume resume);

    WorkExperience save(WorkExperience workExperience);

    void delete(WorkExperience workExperience);
}
