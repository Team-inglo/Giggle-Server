package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PartTimeEmploymentPermitRepository extends JpaRepository<PartTimeEmploymentPermit, Long>{
    Optional<PartTimeEmploymentPermit> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);
}
