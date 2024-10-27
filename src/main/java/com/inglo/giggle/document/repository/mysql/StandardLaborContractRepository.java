package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.StandardLaborContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardLaborContractRepository extends JpaRepository<StandardLaborContract, Long>{
    Optional<StandardLaborContract> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);
}
