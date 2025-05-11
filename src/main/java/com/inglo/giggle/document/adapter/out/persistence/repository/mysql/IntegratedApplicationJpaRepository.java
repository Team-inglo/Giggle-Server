package com.inglo.giggle.document.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.document.adapter.out.persistence.entity.IntegratedApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntegratedApplicationJpaRepository extends JpaRepository<IntegratedApplicationEntity, Long> {
    Optional<IntegratedApplicationEntity> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);
}
