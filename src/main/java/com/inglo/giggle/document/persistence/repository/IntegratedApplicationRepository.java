package com.inglo.giggle.document.persistence.repository;

import com.inglo.giggle.document.domain.IntegratedApplication;

public interface IntegratedApplicationRepository {

    IntegratedApplication findByIdOrElseThrow(Long id);

    IntegratedApplication findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    IntegratedApplication findWithSchoolByIdOrElseThrow(Long documentId);

    IntegratedApplication save(IntegratedApplication integratedApplication);
}
