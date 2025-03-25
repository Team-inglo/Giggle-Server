package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;

public interface PartTimeEmploymentPermitRepository {

    PartTimeEmploymentPermit findByIdOrElseThrow(Long id);

    PartTimeEmploymentPermit findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    void save(PartTimeEmploymentPermit partTimeEmploymentPermit);

    void delete(PartTimeEmploymentPermit partTimeEmploymentPermit);

    void deleteById(Long id);
}
