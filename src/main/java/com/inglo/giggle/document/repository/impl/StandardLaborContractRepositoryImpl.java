package com.inglo.giggle.document.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.repository.StandardLaborContractRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StandardLaborContractRepositoryImpl implements StandardLaborContractRepository {

    private final StandardLaborContractJpaRepository standardLaborContractJpaRepository;

    @Override
    public StandardLaborContract findByIdOrElseThrow(Long id) {
        return standardLaborContractJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_STANDARD_LABOR_CONTRACT));
    }

    @Override
    public StandardLaborContract findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return standardLaborContractJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId).orElse(null);
    }

    @Override
    public StandardLaborContract findWithWeeklyRestDaysAndInsurancesByIdOrElseNull(Long documentId) {
        return standardLaborContractJpaRepository.findWithWeeklyRestDaysAndInsurancesById(documentId).orElse(null);
    }

    @Override
    public void save(StandardLaborContract standardLaborContract) {
        standardLaborContractJpaRepository.save(standardLaborContract);
    }
}
