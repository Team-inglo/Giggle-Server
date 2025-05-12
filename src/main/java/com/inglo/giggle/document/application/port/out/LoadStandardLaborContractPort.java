package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.StandardLaborContract;

public interface LoadStandardLaborContractPort {

    StandardLaborContract loadAllStandardLaborContractOrElseThrow(Long id);

    StandardLaborContract loadStandardLaborContractWithContractWorkDayTimesOrElseThrow(Long id);

    StandardLaborContract loadStandardLaborContractWithRejectsByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    StandardLaborContract loadStandardLaborContractByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);
}
