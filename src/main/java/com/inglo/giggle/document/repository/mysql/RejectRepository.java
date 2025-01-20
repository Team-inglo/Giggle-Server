package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.Reject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RejectRepository extends JpaRepository<Reject, Long>{
    Optional<Reject> findTopByDocumentIdOrderByCreatedAtDesc(Long id);

    List<Reject> findAllByDocumentId(Long id);
}
