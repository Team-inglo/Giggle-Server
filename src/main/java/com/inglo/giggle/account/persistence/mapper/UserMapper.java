package com.inglo.giggle.account.persistence.mapper;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.entity.UserEntity;
import com.inglo.giggle.address.persistence.mapper.AddressMapper;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .provider(entity.getProvider())
                .serialId(entity.getSerialId())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .profileImgUrl(entity.getProfileImgUrl())
                .phoneNumber(entity.getPhoneNumber())
                .marketingAllowed(entity.getMarketingAllowed())
                .notificationAllowed(entity.getNotificationAllowed())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .nationality(entity.getNationality())
                .language(entity.getLanguage())
                .birth(entity.getBirth())
                .visa(entity.getVisa())
                .address(AddressMapper.toDomain(entity.getAddressEntity()))
                .build();
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        return UserEntity.builder()
                .id(domain.getId())
                .provider(domain.getProvider())
                .serialId(domain.getSerialId())
                .password(domain.getPassword())
                .email(domain.getEmail())
                .profileImgUrl(domain.getProfileImgUrl())
                .phoneNumber(domain.getPhoneNumber())
                .marketingAllowed(domain.getMarketingAllowed())
                .notificationAllowed(domain.getNotificationAllowed())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .gender(domain.getGender())
                .nationality(domain.getNationality())
                .language(domain.getLanguage())
                .birth(domain.getBirth())
                .visa(domain.getVisa())
                .addressEntity(AddressMapper.toEntity(domain.getAddress()))
                .build();
    }
}
