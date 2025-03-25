package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.StandardLaborContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StandardLaborContractJpaRepository extends JpaRepository<StandardLaborContract, Long>{
    Optional<StandardLaborContract> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);

    @Query("SELECT slc FROM StandardLaborContract slc " +
            "JOIN FETCH slc.weeklyRestDays " +
            "JOIN FETCH slc.insurances " +
            "WHERE slc.id = :documentId"
    )
    Optional<StandardLaborContract> findWithWeeklyRestDaysAndInsurancesById(@Param("documentId") Long documentId);
}
