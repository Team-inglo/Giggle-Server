package com.inglo.giggle.document.domain.service;

import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.StandardLaborContract;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ContractWorkDayTimeService {
    public ContractWorkDayTime createContractWorkDayTime(
            EDayOfWeek eDayOfWeek,
            LocalTime workStartTime,
            LocalTime workEndTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime,
            StandardLaborContract standardLaborContract
            ) {
        return ContractWorkDayTime.builder()
                .dayOfWeek(eDayOfWeek)
                .workStartTime(workStartTime)
                .workEndTime(workEndTime)
                .breakStartTime(breakStartTime)
                .breakEndTime(breakEndTime)
                .standardLaborContract(standardLaborContract)
                .build();
    }
}
