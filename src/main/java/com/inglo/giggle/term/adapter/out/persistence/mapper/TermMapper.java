package com.inglo.giggle.term.adapter.out.persistence.mapper;

import com.inglo.giggle.term.adapter.out.persistence.entity.TermEntity;
import com.inglo.giggle.term.domain.Term;
import org.springframework.stereotype.Component;

@Component
public class TermMapper {
    public Term toDomain(TermEntity entity) {
        if (entity == null) {
            return null;
        }

        return Term.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .termType(entity.getTermType())
                .version(entity.getVersion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public TermEntity toEntity(Term domain) {
        if (domain == null) {
            return null;
        }

        return TermEntity.builder()
                .id(domain.getId())
                .content(domain.getContent())
                .termType(domain.getTermType())
                .version(domain.getVersion())
                .build();
    }
}
