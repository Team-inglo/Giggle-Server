package com.inglo.giggle.account.persistence.mapper;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.persistence.entity.OwnerEntity;
import com.inglo.giggle.address.persistence.mapper.AddressMapper;

public class OwnerMapper {
    public static Owner toDomain(OwnerEntity entity) {
        if (entity == null) {
            return null;
        }
        return Owner.builder()
                .id(entity.getId())
                .provider(entity.getProvider())
                .serialId(entity.getSerialId())
                .password(entity.getPassword())
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

    public static OwnerEntity toEntity(Owner domain) {
        if (domain == null) {
            return null;
        }
        return OwnerEntity.builder()
                .id(domain.getId())
                .provider(domain.getProvider())
                .serialId(domain.getSerialId())
                .password(domain.getPassword())
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
