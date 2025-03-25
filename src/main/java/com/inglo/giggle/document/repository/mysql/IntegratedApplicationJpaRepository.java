package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.IntegratedApplication;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntegratedApplicationJpaRepository extends JpaRepository<IntegratedApplication, Long> {
    Optional<IntegratedApplication> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);

    @EntityGraph(attributePaths = {"school"})
    Optional<IntegratedApplication> findWithSchoolById(Long documentId);
}
