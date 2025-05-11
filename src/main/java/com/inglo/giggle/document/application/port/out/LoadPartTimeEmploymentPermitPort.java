package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;

public interface LoadPartTimeEmploymentPermitPort {

    PartTimeEmploymentPermit loadPartTimeEmploymentPermit(Long id);

    PartTimeEmploymentPermit loadPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

}
