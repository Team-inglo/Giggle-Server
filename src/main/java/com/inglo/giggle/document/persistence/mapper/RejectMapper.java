package com.inglo.giggle.document.persistence.mapper;

import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.persistence.entity.RejectEntity;

import java.util.List;

public class RejectMapper {
    public static Reject toDomain(RejectEntity entity) {
        if (entity == null) {
            return null;
        }
        return Reject.builder()
                .id(entity.getId())
                .reason(entity.getReason())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .documentId(entity.getDocumentEntity() != null ? entity.getDocumentEntity().getId() : null)
                .build();
    }

    public static RejectEntity toEntity(Reject domain) {
        if (domain == null) {
            return null;
        }
        return RejectEntity.builder()
                .reason(domain.getReason())
                .build();
    }

    public static List<Reject> toDomains(List<RejectEntity> entities) {
        return entities.stream()
                .map(RejectMapper::toDomain)
                .toList();
    }

    public static List<RejectEntity> toEntities(List<Reject> domains) {
        return domains.stream()
                .map(RejectMapper::toEntity)
                .toList();
    }
}
