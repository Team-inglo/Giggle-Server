package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;

import java.util.List;
import java.util.UUID;

public interface EducationRepository {

    Education findByIdOrElseThrow(Long id);

    List<Education> findEducationByAccountIdAndEducationLevel(UUID userId, EEducationLevel educationLevel);

    List<Education> findAllByResume(Resume resume);

    Education findWithSchoolByIdOrElseThrow(Long id);

    Education save(Education education);

    void delete(Education education);
}
