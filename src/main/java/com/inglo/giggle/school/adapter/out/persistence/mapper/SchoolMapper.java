package com.inglo.giggle.school.adapter.out.persistence.mapper;

import com.inglo.giggle.core.persistence.AddressMapper;
import com.inglo.giggle.school.adapter.out.persistence.entity.SchoolEntity;
import com.inglo.giggle.school.domain.School;
import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {
    public School toDomain(SchoolEntity entity) {
        if (entity == null) {
            return null;
        }
        return School.builder()
                .id(entity.getId())
                .schoolName(entity.getSchoolName())
                .schoolPhoneNumber(entity.getSchoolPhoneNumber())
                .instituteName(entity.getInstituteName())
                .coordinatorName(entity.getCoordinatorName())
                .coordinatorPhoneNumber(entity.getCoordinatorPhoneNumber())
                .isMetropolitan(entity.getIsMetropolitan())
                .address(AddressMapper.toDomain(entity.getAddressEntity()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public SchoolEntity toEntity(School domain) {
        if (domain == null) {
            return null;
        }
        return SchoolEntity.builder()
                .id(domain.getId())
                .schoolName(domain.getSchoolName())
                .schoolPhoneNumber(domain.getSchoolPhoneNumber())
                .instituteName(domain.getInstituteName())
                .coordinatorName(domain.getCoordinatorName())
                .coordinatorPhoneNumber(domain.getCoordinatorPhoneNumber())
                .isMetropolitan(domain.getIsMetropolitan())
                .addressEntity(AddressMapper.toEntity(domain.getAddress()))
                .build();
    }
}
