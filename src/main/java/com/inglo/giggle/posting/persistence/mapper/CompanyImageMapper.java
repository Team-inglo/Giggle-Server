package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.persistence.entity.CompanyImageEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class CompanyImageMapper {
    public static CompanyImage toDomain(CompanyImageEntity entity) {
        if (entity == null) {
            return null;
        }
        return CompanyImage.builder()
                .id(entity.getId())
                .imgUrl(entity.getImgUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .jobPostingId(isInitialized(entity.getJobPostingEntity()) ? entity.getJobPostingEntity().getId() : null)
                .build();
    }

    public static CompanyImageEntity toEntity(CompanyImage domain) {
        if (domain == null) {
            return null;
        }
        return CompanyImageEntity.builder()
                .imgUrl(domain.getImgUrl())
                .build();
    }

    public static List<CompanyImage> toDomains(List<CompanyImageEntity> entities) {
        return entities.stream()
                .map(CompanyImageMapper::toDomain)
                .toList();
    }

    public static List<CompanyImageEntity> toEntities(List<CompanyImage> domains) {
        return domains.stream()
                .map(CompanyImageMapper::toEntity)
                .toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
