package com.inglo.giggle.version.adapter.out.persistence.mapper;

import com.inglo.giggle.version.adapter.out.persistence.entity.VersionEntity;
import com.inglo.giggle.version.domain.Version;
import org.springframework.stereotype.Component;

@Component
public class VersionMapper {
    public Version toDomain(VersionEntity entity) {
        if (entity == null) {
            return null;
        }
        return Version.builder()
                .id(entity.getId())
                .major(entity.getMajor())
                .minor(entity.getMinor())
                .patch(entity.getPatch())
                .osType(entity.getOsType())
                .build();
    }

    public VersionEntity toEntity(Version domain) {
        if (domain == null) {
            return null;
        }
        return VersionEntity.builder()
                .id(domain.getId())
                .major(domain.getMajor())
                .minor(domain.getMinor())
                .patch(domain.getPatch())
                .osType(domain.getOsType())
                .build();
    }
}
