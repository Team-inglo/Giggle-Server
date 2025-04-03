package com.inglo.giggle.document.persistence.mapper;

import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.persistence.entity.ContractWorkDayTimeEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
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
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .standardLaborContractId(isInitialized(entity.getStandardLaborContractEntity()) ? entity.getStandardLaborContractEntity().getId() : null)
                .build();
    }

    public static ContractWorkDayTimeEntity toEntity(ContractWorkDayTime domain) {
        if (domain == null) {
            return null;
        }
        return ContractWorkDayTimeEntity.builder()
                .dayOfWeek(domain.getDayOfWeek())
                .workStartTime(domain.getWorkStartTime())
                .workEndTime(domain.getWorkEndTime())
                .breakStartTime(domain.getBreakStartTime())
                .breakEndTime(domain.getBreakEndTime())
                .build();
    }

    public static List<ContractWorkDayTime> toDomains(List<ContractWorkDayTimeEntity> entities) {
        return entities.stream().map(ContractWorkDayTimeMapper::toDomain).toList();
    }

    public static List<ContractWorkDayTimeEntity> toEntities(List<ContractWorkDayTime> domains) {
        return domains.stream().map(ContractWorkDayTimeMapper::toEntity).toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}

