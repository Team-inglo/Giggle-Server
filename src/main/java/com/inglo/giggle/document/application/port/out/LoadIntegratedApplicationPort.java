package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.IntegratedApplication;

public interface LoadIntegratedApplicationPort {

    IntegratedApplication loadIntegratedApplicationOrElseThrow(Long id);

    IntegratedApplication loadIntegratedApplicationByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);
}
