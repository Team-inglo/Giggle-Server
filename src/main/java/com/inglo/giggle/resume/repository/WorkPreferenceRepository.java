package com.inglo.giggle.resume.repository;

import com.inglo.giggle.resume.domain.WorkPreference;

import java.util.List;
import java.util.UUID;

public interface WorkPreferenceRepository {

    WorkPreference findByIdOrElseThrow(Long id);

    List<WorkPreference> findAllByResumeId(UUID resumeId);

    void save(WorkPreference workPreference);

    void deleteById(Long workPreferenceId);
}
