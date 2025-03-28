package com.inglo.giggle.account.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OwnerJpaRepository extends JpaRepository<Owner, UUID>{

    @Query("SELECT o FROM Owner o " +
            "JOIN UserOwnerJobPosting uojp ON o.id = uojp.owner.id " +
            "JOIN Document d ON d.userOwnerJobPosting.id = uojp.id " +
            "WHERE d.id = :documentId"
    )
    Optional<Owner> findByDocumentId(@Param("documentId") Long documentId);
}
