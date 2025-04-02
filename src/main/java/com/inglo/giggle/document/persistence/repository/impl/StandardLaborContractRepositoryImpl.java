package com.inglo.giggle.document.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.entity.StandardLaborContractEntity;
import com.inglo.giggle.document.persistence.mapper.StandardLaborContractMapper;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.document.persistence.repository.mysql.StandardLaborContractJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StandardLaborContractRepositoryImpl implements StandardLaborContractRepository {

    private final StandardLaborContractJpaRepository standardLaborContractJpaRepository;

    @Override
    public StandardLaborContract findByIdOrElseThrow(Long id) {
        return StandardLaborContractMapper.toDomain(standardLaborContractJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_STANDARD_LABOR_CONTRACT)));
    }

    @Override
    public StandardLaborContract findByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return StandardLaborContractMapper.toDomain(standardLaborContractJpaRepository.findByUserOwnerJobPostingEntityId(userOwnerJobPostingId)
                .orElse(null));
    }

    @Override
    public StandardLaborContract findWithWeeklyRestDaysAndInsurancesByIdOrElseNull(Long documentId) {
        return StandardLaborContractMapper.toDomain(standardLaborContractJpaRepository.findWithWeeklyRestDaysAndInsurancesById(documentId)
                .orElse(null));
    }

    @Override
    public StandardLaborContract save(StandardLaborContract standardLaborContract) {
        StandardLaborContractEntity entity = standardLaborContractJpaRepository.save(StandardLaborContractMapper.toEntity(standardLaborContract));
        return StandardLaborContractMapper.toDomain(entity);
    }
}
