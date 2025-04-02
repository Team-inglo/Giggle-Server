package com.inglo.giggle.document.persistence.repository.mysql;

import com.inglo.giggle.document.persistence.entity.RejectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RejectJpaRepository extends JpaRepository<RejectEntity, Long>{
    Optional<RejectEntity> findTopByDocumentEntityIdOrderByCreatedAtDesc(Long id);

    List<RejectEntity> findAllByDocumentEntityId(Long id);
}
