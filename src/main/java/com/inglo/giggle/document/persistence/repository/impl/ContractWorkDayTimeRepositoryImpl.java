package com.inglo.giggle.document.persistence.repository.impl;

import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.persistence.entity.ContractWorkDayTimeEntity;
import com.inglo.giggle.document.persistence.mapper.ContractWorkDayTimeMapper;
import com.inglo.giggle.document.persistence.repository.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.persistence.repository.mysql.ContractWorkDayTimeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContractWorkDayTimeRepositoryImpl implements ContractWorkDayTimeRepository {

    private final ContractWorkDayTimeJpaRepository contractWorkDayTimeJpaRepository;

    @Override
    public List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId) {
        return ContractWorkDayTimeMapper.toDomains(contractWorkDayTimeJpaRepository.findByStandardLaborContractEntityId(standardLaborContractId));
    }

    @Override
    public ContractWorkDayTime save(ContractWorkDayTime contractWorkDayTime) {
        ContractWorkDayTimeEntity entity = ContractWorkDayTimeMapper.toEntity(contractWorkDayTime);
        return ContractWorkDayTimeMapper.toDomain(contractWorkDayTimeJpaRepository.save(entity));
    }

    @Override
    public void deleteAll(List<ContractWorkDayTime> contractWorkDayTimes) {
        contractWorkDayTimeJpaRepository.deleteAll(ContractWorkDayTimeMapper.toEntities(contractWorkDayTimes));
    }

}
