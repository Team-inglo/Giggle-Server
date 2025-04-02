package com.inglo.giggle.document.persistence.repository;

import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;

public interface PartTimeEmploymentPermitRepository {

    PartTimeEmploymentPermit findByIdOrElseThrow(Long id);

    PartTimeEmploymentPermit findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    PartTimeEmploymentPermit save(PartTimeEmploymentPermit partTimeEmploymentPermit);
}
