package com.inglo.giggle.admin.adapter.out.persistence.mapper;

import com.inglo.giggle.admin.adapter.out.persistence.entity.AdminEntity;
import com.inglo.giggle.admin.domain.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public Admin toDomain(AdminEntity entity) {
        if (entity == null) {
            return null;
        }
        return Admin.builder()
                .id(entity.getId())
                .build();
    }

    public AdminEntity toEntity(Admin domain) {
        if (domain == null) {
            return null;
        }
        return AdminEntity.builder()
                .id(domain.getId())
                .build();
    }
}
