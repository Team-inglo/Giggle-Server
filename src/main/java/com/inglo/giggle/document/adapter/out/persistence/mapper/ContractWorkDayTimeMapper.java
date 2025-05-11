package com.inglo.giggle.document.adapter.out.persistence.mapper;

import com.inglo.giggle.document.adapter.out.persistence.entity.ContractWorkDayTimeEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.StandardLaborContractEntity;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import org.springframework.stereotype.Component;

@Component
public class ContractWorkDayTimeMapper {
    public ContractWorkDayTime toDomain(
            ContractWorkDayTimeEntity entity
    ) {
        if (entity == null) {
            return null;
        }
        return ContractWorkDayTime.builder()
                .id(entity.getId())
                .dayOfWeek(entity.getDayOfWeek())
                .workStartTime(entity.getWorkStartTime())
                .workEndTime(entity.getWorkEndTime())
                .breakStartTime(entity.getBreakStartTime())
                .breakEndTime(entity.getBreakEndTime())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public ContractWorkDayTimeEntity toEntity(
            ContractWorkDayTime domain,
            StandardLaborContractEntity standardLaborContractEntity
    ) {
        if (domain == null) {
            return null;
        }
        return ContractWorkDayTimeEntity.builder()
                .id(domain.getId())
                .dayOfWeek(domain.getDayOfWeek())
                .workStartTime(domain.getWorkStartTime())
                .workEndTime(domain.getWorkEndTime())
                .breakStartTime(domain.getBreakStartTime())
                .breakEndTime(domain.getBreakEndTime())
                .standardLaborContractEntity(standardLaborContractEntity)
                .build();
    }
}

