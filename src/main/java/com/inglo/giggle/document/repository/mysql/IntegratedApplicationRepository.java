package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.IntegratedApplication;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegratedApplicationRepository extends JpaRepository<IntegratedApplication, Long> {
    Optional<IntegratedApplication> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);

    @EntityGraph(attributePaths = {"school"})
    Optional<IntegratedApplication> findWithSchoolById(Long documentId);
}
