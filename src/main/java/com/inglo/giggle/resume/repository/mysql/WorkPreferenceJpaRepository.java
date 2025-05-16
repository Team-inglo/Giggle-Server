package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.WorkPreference;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkPreferenceJpaRepository extends JpaRepository<WorkPreference, Long> {

    @EntityGraph(attributePaths = {"jobCategories", "employmentTypes", "preferenceAddresses"})
    Optional<WorkPreference> findByResumeAccountId(UUID resumeId);

}
