package com.inglo.giggle.account.persistence.mapper;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.persistence.entity.OwnerEntity;
import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.posting.persistence.mapper.JobPostingMapper;
import com.inglo.giggle.security.persistence.mapper.AccountDeviceMapper;
import com.inglo.giggle.term.persistence.mapper.TermAccountMapper;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;

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
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .termAccounts(isInitialized(entity.getTermAccountEntities()) ? TermAccountMapper.toDomains(entity.getTermAccountEntities()) : null)
                .accountDevices(isInitialized(entity.getAccountDeviceEntities()) ? AccountDeviceMapper.toDomains(entity.getAccountDeviceEntities()) : null)
                .companyName(entity.getCompanyName())
                .ownerName(entity.getOwnerName())
                .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
                .address(AddressMapper.toDomain(entity.getAddressEntity()))
                .jobPostings(isInitialized(entity.getJobPostingEntities()) ? JobPostingMapper.toDomains(entity.getJobPostingEntities()) : null)
                .build();
    }

    public static OwnerEntity toEntity(Owner domain) {
        if (domain == null) {
            return null;
        }
        return OwnerEntity.builder()
                .provider(domain.getProvider())
                .serialId(domain.getSerialId())
                .password(domain.getPassword())
                .email(domain.getEmail())
                .profileImgUrl(domain.getProfileImgUrl())
                .phoneNumber(domain.getPhoneNumber())
                .marketingAllowed(domain.getMarketingAllowed())
                .notificationAllowed(domain.getNotificationAllowed())
                .termAccountEntities(domain.getTermAccounts() != null && !domain.getTermAccounts().isEmpty() ? TermAccountMapper.toEntities(domain.getTermAccounts()) : null)
                .accountDeviceEntities(domain.getAccountDevices() != null && !domain.getAccountDevices().isEmpty() ? AccountDeviceMapper.toEntities(domain.getAccountDevices()) : null)
                .companyName(domain.getCompanyName())
                .ownerName(domain.getOwnerName())
                .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
                .addressEntity(AddressMapper.toEntity(domain.getAddress()))
                .jobPostingEntities(domain.getJobPostings() != null && !domain.getJobPostings().isEmpty() ? JobPostingMapper.toEntities(domain.getJobPostings()) : null)
                .build();
    }

    private static boolean isInitialized(Collection<?> collection) {
        return collection instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) collection).wasInitialized();
    }
}
