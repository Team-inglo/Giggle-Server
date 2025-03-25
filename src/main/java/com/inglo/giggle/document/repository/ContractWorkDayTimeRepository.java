package com.inglo.giggle.document.repository;

import com.inglo.giggle.document.domain.ContractWorkDayTime;

import java.util.List;

public interface ContractWorkDayTimeRepository {

    List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId);

    void save(ContractWorkDayTime contractWorkDayTime);

    void deleteAll(List<ContractWorkDayTime> contractWorkDayTimes);
}
