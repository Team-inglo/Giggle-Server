package com.inglo.giggle.version.persistence.mapper;

import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.persistence.entity.VersionEntity;

import java.util.List;

public class VersionMapper {
    public static Version toDomain(VersionEntity entity) {
        return Version.builder()
                .id(entity.getId())
                .major(entity.getMajor())
                .minor(entity.getMinor())
                .patch(entity.getPatch())
                .osType(entity.getOsType())
                .build();
    }

    public static VersionEntity toEntity(Version domain) {
        return VersionEntity.builder()
                .id(domain.getId())
                .major(domain.getMajor())
                .minor(domain.getMinor())
                .patch(domain.getPatch())
                .osType(domain.getOsType())
                .build();
    }

    public static List<Version> toDomains(List<VersionEntity> entities) {
        return entities.stream()
                .map(VersionMapper::toDomain)
                .toList();
    }

    public static List<VersionEntity> toEntities(List<Version> domains) {
        return domains.stream()
                .map(VersionMapper::toEntity)
                .toList();
    }

}
