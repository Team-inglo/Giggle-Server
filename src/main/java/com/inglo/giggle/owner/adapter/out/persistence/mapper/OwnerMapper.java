package com.inglo.giggle.owner.adapter.out.persistence.mapper;

import com.inglo.giggle.owner.domain.Owner;
import com.inglo.giggle.core.persistence.AddressMapper;
import com.inglo.giggle.owner.adapter.out.persistence.entity.OwnerEntity;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {
    public Owner toDomain(OwnerEntity entity) {
        if (entity == null) {
            return null;
        }
        return Owner.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .profileImgUrl(entity.getProfileImgUrl())
                .phoneNumber(entity.getPhoneNumber())
                .marketingAllowed(entity.getMarketingAllowed())
                .notificationAllowed(entity.getNotificationAllowed())
                .companyName(entity.getCompanyName())
                .ownerName(entity.getOwnerName())
                .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
                .address(AddressMapper.toDomain(entity.getAddressEntity()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public OwnerEntity toEntity(Owner domain) {
        if (domain == null) {
            return null;
        }
        return OwnerEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .profileImgUrl(domain.getProfileImgUrl())
                .phoneNumber(domain.getPhoneNumber())
                .marketingAllowed(domain.getMarketingAllowed())
                .notificationAllowed(domain.getNotificationAllowed())
                .companyName(domain.getCompanyName())
                .ownerName(domain.getOwnerName())
                .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
                .addressEntity(AddressMapper.toEntity(domain.getAddress()))
                .build();
    }
}
