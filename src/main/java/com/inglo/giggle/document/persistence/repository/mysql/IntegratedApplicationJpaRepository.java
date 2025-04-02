package com.inglo.giggle.document.persistence.repository.mysql;

import com.inglo.giggle.document.persistence.entity.IntegratedApplicationEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntegratedApplicationJpaRepository extends JpaRepository<IntegratedApplicationEntity, Long> {
    Optional<IntegratedApplicationEntity> findByUserOwnerJobPostingEntityId(Long userOwnerJobPostingId);

    @EntityGraph(attributePaths = {"school"})
    Optional<IntegratedApplicationEntity> findWithSchoolById(Long documentId);
}
