package com.inglo.giggle.document.persistence.repository;

import com.inglo.giggle.document.domain.ContractWorkDayTime;

import java.util.List;

public interface ContractWorkDayTimeRepository {

    List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId);

    ContractWorkDayTime save(ContractWorkDayTime contractWorkDayTimeEntity);

    void deleteAll(List<ContractWorkDayTime> contractWorkDayTimeEntities);
}
