package com.inglo.giggle.document.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
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
    public ContractWorkDayTime findByIdOrElseNull(Long id) {
        return contractWorkDayTimeJpaRepository.findById(id).orElse(null);
    }

    @Override
    public ContractWorkDayTime findByIdOrElseThrow(Long id) {
        return contractWorkDayTimeJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CONTRACT_WORK_DAY_TIME));
    }

    @Override
    public List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId) {
        return contractWorkDayTimeJpaRepository.findByStandardLaborContractId(standardLaborContractId);
    }

    @Override
    public void save(ContractWorkDayTime contractWorkDayTime) {
        contractWorkDayTimeJpaRepository.save(contractWorkDayTime);
    }

    @Override
    public void delete(ContractWorkDayTime contractWorkDayTime) {
        contractWorkDayTimeJpaRepository.delete(contractWorkDayTime);
    }

    @Override
    public void deleteById(Long id) {
        contractWorkDayTimeJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<ContractWorkDayTime> contractWorkDayTimes) {
        contractWorkDayTimeJpaRepository.deleteAll(contractWorkDayTimes);
    }

}
