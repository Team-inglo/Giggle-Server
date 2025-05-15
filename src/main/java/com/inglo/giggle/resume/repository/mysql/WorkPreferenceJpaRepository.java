package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.WorkPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkPreferenceJpaRepository extends JpaRepository<WorkPreference, Long> {

    List<WorkPreference> findAllByResumeAccountId(UUID resumeId);
}
