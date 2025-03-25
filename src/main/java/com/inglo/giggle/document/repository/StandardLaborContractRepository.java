package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.StandardLaborContract;

public interface StandardLaborContractRepository {

    StandardLaborContract findByIdOrElseThrow(Long id);

    StandardLaborContract findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    StandardLaborContract findWithWeeklyRestDaysAndInsurancesByIdOrElseNull(Long documentId);

    void save(StandardLaborContract standardLaborContract);

    void delete(StandardLaborContract standardLaborContract);

    void deleteById(Long id);
}
