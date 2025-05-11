package com.inglo.giggle.user.adapter.out.persistence.mapper;

import com.inglo.giggle.core.persistence.AddressMapper;
import com.inglo.giggle.user.adapter.out.persistence.entity.UserEntity;
import com.inglo.giggle.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
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
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        return UserEntity.builder()
                .id(domain.getId())
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
