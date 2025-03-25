package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.ContractWorkDayTime;

import java.util.List;

public interface ContractWorkDayTimeRepository {

    ContractWorkDayTime findByIdOrElseNull(Long id);

    ContractWorkDayTime findByIdOrElseThrow(Long id);

    List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId);

    void save(ContractWorkDayTime contractWorkDayTime);

    void delete(ContractWorkDayTime contractWorkDayTime);

    void deleteById(Long id);

    void deleteAll(List<ContractWorkDayTime> contractWorkDayTimes);
}
