package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.ContractWorkDayTime;

import java.util.List;

public interface LoadContractWorkDayTimePort {

    List<ContractWorkDayTime> loadContractWorkDayTime(Long standardLaborContractId);
}
