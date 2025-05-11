package com.inglo.giggle.owner.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.owner.adapter.out.persistence.entity.OwnerEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, UUID>{

    @Query("SELECT o FROM OwnerEntity o " +
            "JOIN UserOwnerJobPostingEntity uojp ON o.id = uojp.ownerId " +
            "JOIN DocumentEntity d ON d.userOwnerJobPostingId = uojp.id " +
            "WHERE d.id = :documentId"
    )
    Optional<OwnerEntity> findByDocumentId(@Param("documentId") Long documentId);
}
