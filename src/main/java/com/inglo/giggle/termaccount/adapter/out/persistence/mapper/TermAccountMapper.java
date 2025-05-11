package com.inglo.giggle.termaccount.adapter.out.persistence.mapper;

import com.inglo.giggle.termaccount.adapter.out.persistence.entity.TermAccountEntity;
import com.inglo.giggle.termaccount.domain.TermAccount;
import org.springframework.stereotype.Component;

@Component
public class TermAccountMapper {
    public TermAccount toDomain(TermAccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return TermAccount.builder()
                .id(entity.getId())
                .isAccepted(entity.getIsAccepted())
                .accountId(entity.getAccountId())
                .termId(entity.getTermId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public TermAccountEntity toEntity(TermAccount domain) {
        if (domain == null) {
            return null;
        }

        return TermAccountEntity.builder()
                .id(domain.getId())
                .isAccepted(domain.getIsAccepted())
                .accountId(domain.getAccountId())
                .termId(domain.getTermId())
                .build();
    }
}
