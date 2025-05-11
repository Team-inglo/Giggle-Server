package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.IntegratedApplication;

public interface LoadIntegratedApplicationPort {

    IntegratedApplication loadIntegratedApplication(Long id);

    IntegratedApplication loadIntegratedApplicationByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);
}
