package com.inglo.giggle.resume.persistence.repository;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;

import java.util.List;
import java.util.UUID;

public interface EducationRepository {

    Education findByIdOrElseThrow(Long id);

    List<Education> findEducationByAccountIdAndEducationLevel(UUID userId, EEducationLevel educationLevel);

    List<Education> findAllByResumeId(UUID resumeId);

    Education save(Education education);

    void saveAll(List<Education> educations);

    void delete(Education education);
}
