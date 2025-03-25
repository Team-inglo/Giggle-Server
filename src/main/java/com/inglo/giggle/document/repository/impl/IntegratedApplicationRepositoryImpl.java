package com.inglo.giggle.document.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.repository.IntegratedApplicationRepository;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IntegratedApplicationRepositoryImpl implements IntegratedApplicationRepository {

    private final IntegratedApplicationJpaRepository integratedApplicationJpaRepository;

    @Override
    public IntegratedApplication findByIdOrElseThrow(Long id) {
        return integratedApplicationJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_INTEGRATED_APPLICATION));
    }

    @Override
    public IntegratedApplication findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return integratedApplicationJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId).orElse(null);
    }

    @Override
    public IntegratedApplication findWithSchoolByIdOrElseThrow(Long documentId) {
        return integratedApplicationJpaRepository.findWithSchoolById(documentId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_INTEGRATED_APPLICATION));
    }

    @Override
    public void save(IntegratedApplication integratedApplication) {
        integratedApplicationJpaRepository.save(integratedApplication);
    }
}
