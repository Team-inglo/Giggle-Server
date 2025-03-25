package com.inglo.giggle.document.repository.impl;

import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.repository.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.repository.mysql.ContractWorkDayTimeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContractWorkDayTimeRepositoryImpl implements ContractWorkDayTimeRepository {

    private final ContractWorkDayTimeJpaRepository contractWorkDayTimeJpaRepository;

    @Override
    public List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId) {
        return contractWorkDayTimeJpaRepository.findByStandardLaborContractId(standardLaborContractId);
    }

    @Override
    public void save(ContractWorkDayTime contractWorkDayTime) {
        contractWorkDayTimeJpaRepository.save(contractWorkDayTime);
    }

    @Override
    public void deleteAll(List<ContractWorkDayTime> contractWorkDayTimes) {
        contractWorkDayTimeJpaRepository.deleteAll(contractWorkDayTimes);
    }

}
