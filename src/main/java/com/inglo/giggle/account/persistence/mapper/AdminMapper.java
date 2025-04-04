package com.inglo.giggle.account.persistence.mapper;

import com.inglo.giggle.account.domain.Admin;
import com.inglo.giggle.account.persistence.entity.AdminEntity;

public class AdminMapper {
    public static Admin toDomain(AdminEntity entity) {
        if (entity == null) {
            return null;
        }
        return Admin.builder()
                .id(entity.getId())
                .provider(entity.getProvider())
                .serialId(entity.getSerialId())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .build();
    }

    public static AdminEntity toEntity(Admin domain) {
        if (domain == null) {
            return null;
        }
        return AdminEntity.builder()
                .id(domain.getId())
                .provider(domain.getProvider())
                .serialId(domain.getSerialId())
                .password(domain.getPassword())
                .email(domain.getEmail())
                .build();
    }
}
