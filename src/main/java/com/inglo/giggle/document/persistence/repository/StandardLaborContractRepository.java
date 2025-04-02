package com.inglo.giggle.document.persistence.repository;

import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.entity.StandardLaborContractEntity;

public interface StandardLaborContractRepository {

    StandardLaborContract findByIdOrElseThrow(Long id);

    StandardLaborContract findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    StandardLaborContract findWithWeeklyRestDaysAndInsurancesByIdOrElseNull(Long documentId);

    StandardLaborContract save(StandardLaborContract standardLaborContract);
}
