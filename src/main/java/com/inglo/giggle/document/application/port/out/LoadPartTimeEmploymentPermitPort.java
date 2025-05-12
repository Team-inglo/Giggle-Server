package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;

public interface LoadPartTimeEmploymentPermitPort {

    PartTimeEmploymentPermit loadPartTimeEmploymentPermitOrElseThrow(Long id);

    PartTimeEmploymentPermit loadAllPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    PartTimeEmploymentPermit loadPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);
}
