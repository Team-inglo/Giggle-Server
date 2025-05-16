package com.inglo.giggle.resume.repository;

import com.inglo.giggle.resume.domain.WorkPreference;

import java.util.UUID;

public interface WorkPreferenceRepository {

    WorkPreference findByResumeIdOrElseThrow(UUID resumeId);

    void save(WorkPreference workPreference);

    void deleteById(Long workPreferenceId);
}
