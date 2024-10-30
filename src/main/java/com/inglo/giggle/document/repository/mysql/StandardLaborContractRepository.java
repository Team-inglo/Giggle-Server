package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.StandardLaborContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardLaborContractRepository extends JpaRepository<StandardLaborContract, Long>{
    Optional<StandardLaborContract> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);

    @Query("SELECT slc FROM StandardLaborContract slc " +
            "JOIN FETCH slc.weeklyRestDays " +
            "JOIN FETCH slc.insurances " +
            "WHERE slc.id = :documentId"
    )
    Optional<StandardLaborContract> findWithWeeklyRestDaysAndInsurancesById(@Param("documentId") Long documentId);
}
