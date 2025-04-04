package com.inglo.giggle.term.persistence.mapper;

import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.persistence.entity.TermEntity;

import java.util.List;

public class TermMapper {
    public static Term toDomain(TermEntity entity) {
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

    public static TermEntity toEntity(Term domain) {
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

    public static List<Term> toDomains(List<TermEntity> entities) {
        return entities.stream()
                .map(TermMapper::toDomain)
                .toList();
    }

    public static List<TermEntity> toEntities(List<Term> domains) {
        return domains.stream()
                .map(TermMapper::toEntity)
                .toList();
    }
}
