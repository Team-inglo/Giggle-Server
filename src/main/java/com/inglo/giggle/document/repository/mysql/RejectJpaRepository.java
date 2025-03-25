package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.Reject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RejectJpaRepository extends JpaRepository<Reject, Long>{
    Optional<Reject> findTopByDocumentIdOrderByCreatedAtDesc(Long id);

    List<Reject> findAllByDocumentId(Long id);
}
