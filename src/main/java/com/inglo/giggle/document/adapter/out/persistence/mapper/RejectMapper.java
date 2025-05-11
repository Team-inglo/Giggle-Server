package com.inglo.giggle.document.adapter.out.persistence.mapper;

import com.inglo.giggle.document.adapter.out.persistence.entity.DocumentEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.RejectEntity;
import com.inglo.giggle.document.domain.Reject;
import org.springframework.stereotype.Component;

@Component
public class RejectMapper {
    public Reject toDomain(RejectEntity entity) {
        if (entity == null) {
            return null;
        }
        return Reject.builder()
                .id(entity.getId())
                .reason(entity.getReason())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public RejectEntity toEntity(
            Reject domain,
            DocumentEntity documentEntity
    ) {
        if (domain == null) {
            return null;
        }
        return RejectEntity.builder()
                .id(domain.getId())
                .reason(domain.getReason())
                .documentEntity(documentEntity)
                .build();
    }
}
