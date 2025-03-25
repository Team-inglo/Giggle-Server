package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartTimeEmploymentPermitJpaRepository extends JpaRepository<PartTimeEmploymentPermit, Long>{
    Optional<PartTimeEmploymentPermit> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);
}
