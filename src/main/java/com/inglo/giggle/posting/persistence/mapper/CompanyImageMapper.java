package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.persistence.entity.CompanyImageEntity;

import java.util.List;

public class CompanyImageMapper {
    public static CompanyImage toDomain(CompanyImageEntity entity) {
        if (entity == null) {
            return null;
        }
        return CompanyImage.builder()
                .id(entity.getId())
                .imgUrl(entity.getImgUrl())
                .jobPostingId(entity.getJobPostingsId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static CompanyImageEntity toEntity(CompanyImage domain) {
        if (domain == null) {
            return null;
        }
        return CompanyImageEntity.builder()
                .id(domain.getId())
                .imgUrl(domain.getImgUrl())
                .jobPostingsId(domain.getJobPostingId())
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
}
