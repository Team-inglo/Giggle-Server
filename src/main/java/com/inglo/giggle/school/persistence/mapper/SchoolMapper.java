package com.inglo.giggle.school.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.entity.SchoolEntity;

import java.util.List;

public class SchoolMapper {
    public static School toDomain(SchoolEntity entity) {
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

    public static SchoolEntity toEntity(School domain) {
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

    public static List<School> toDomains(List<SchoolEntity> entities) {
        return entities.stream()
                .map(SchoolMapper::toDomain)
                .toList();
    }

    public static List<SchoolEntity> toEntities(List<School> domains) {
        return domains.stream()
                .map(SchoolMapper::toEntity)
                .toList();
    }
}
