package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.StandardLaborContract;

public interface LoadStandardLaborContractPort {

    StandardLaborContract loadStandardLaborContract(Long id);

    StandardLaborContract loadStandardLaborContractByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);
}
