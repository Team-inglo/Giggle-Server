package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.StandardLaborContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface StandardLaborContractRepository {

    StandardLaborContract findByIdOrElseThrow(Long id);

    StandardLaborContract findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    StandardLaborContract findWithWeeklyRestDaysAndInsurancesByIdOrElseNull(Long documentId);

    void save(StandardLaborContract standardLaborContract);

    void delete(StandardLaborContract standardLaborContract);

    void deleteById(Long id);
}
