package com.inglo.giggle.document.persistence.mapper;

import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.persistence.entity.ContractWorkDayTimeEntity;

import java.util.List;

public class ContractWorkDayTimeMapper {
    public static ContractWorkDayTime toDomain(ContractWorkDayTimeEntity entity) {
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
                .standardLaborContractId(entity.getStandardLaborContractId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static ContractWorkDayTimeEntity toEntity(ContractWorkDayTime domain) {
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
                .standardLaborContractId(domain.getStandardLaborContractId())
                .build();
    }

    public static List<ContractWorkDayTime> toDomains(List<ContractWorkDayTimeEntity> entities) {
        return entities.stream().map(ContractWorkDayTimeMapper::toDomain).toList();
    }

    public static List<ContractWorkDayTimeEntity> toEntities(List<ContractWorkDayTime> domains) {
        return domains.stream().map(ContractWorkDayTimeMapper::toEntity).toList();
    }
}

