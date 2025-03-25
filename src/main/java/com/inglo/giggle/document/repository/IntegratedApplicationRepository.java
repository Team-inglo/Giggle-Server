package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.IntegratedApplication;

public interface IntegratedApplicationRepository {

    IntegratedApplication findByIdOrElseThrow(Long id);

    IntegratedApplication findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId);

    IntegratedApplication findWithSchoolByIdOrElseThrow(Long documentId);

    void save(IntegratedApplication integratedApplication);

    void delete(IntegratedApplication integratedApplication);

    void deleteById(Long id);
}
