package com.inglo.giggle.document.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.document.adapter.out.persistence.entity.StandardLaborContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StandardLaborContractJpaRepository extends JpaRepository<StandardLaborContractEntity, Long>{
    Optional<StandardLaborContractEntity> findByUserOwnerJobPostingId(Long userOwnerJobPostingId);

    @Query("SELECT slc FROM StandardLaborContractEntity slc " +
            "JOIN FETCH slc.weeklyRestDays " +
            "JOIN FETCH slc.insurances " +
            "WHERE slc.id = :documentId"
    )
    Optional<StandardLaborContractEntity> findWithWeeklyRestDaysAndInsurancesById(@Param("documentId") Long documentId);
}
