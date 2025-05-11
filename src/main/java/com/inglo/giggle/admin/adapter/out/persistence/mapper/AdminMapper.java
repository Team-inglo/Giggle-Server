package com.inglo.giggle.admin.adapter.out.persistence.mapper;

import com.inglo.giggle.admin.adapter.out.persistence.entity.AdminEntity;
import com.inglo.giggle.admin.domain.Admin;

public class AdminMapper {
    public static Admin toDomain(AdminEntity entity) {
        if (entity == null) {
            return null;
        }
        return Admin.builder()
                .id(entity.getId())
                .build();
    }

    public static AdminEntity toEntity(Admin domain) {
        if (domain == null) {
            return null;
        }
        return AdminEntity.builder()
                .id(domain.getId())
                .build();
    }
}
