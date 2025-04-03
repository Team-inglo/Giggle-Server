package com.inglo.giggle.account.persistence.mapper;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.entity.UserEntity;
import com.inglo.giggle.posting.persistence.mapper.BookMarkMapper;
import com.inglo.giggle.posting.persistence.mapper.UserOwnerJobPostingMapper;
import com.inglo.giggle.resume.persistence.mapper.ResumeMapper;
import com.inglo.giggle.security.persistence.mapper.AccountDeviceMapper;
import com.inglo.giggle.term.persistence.mapper.TermAccountMapper;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;

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
                .termAccounts(isInitialized(entity.getTermAccountEntities()) ? TermAccountMapper.toDomains(entity.getTermAccountEntities()) : null)
                .accountDevices(isInitialized(entity.getAccountDeviceEntities()) ? AccountDeviceMapper.toDomains(entity.getAccountDeviceEntities()) : null)
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .nationality(entity.getNationality())
                .language(entity.getLanguage())
                .birth(entity.getBirth())
                .visa(entity.getVisa())
                .bookMarks(isInitialized(entity.getBookMarkEntities()) ? BookMarkMapper.toDomains(entity.getBookMarkEntities()) : null)
                .userOwnerJobPostings(isInitialized(entity.getUserOwnerJobPostingEntities()) ? UserOwnerJobPostingMapper.toDomains(entity.getUserOwnerJobPostingEntities()) : null)
                .resume(isInitialized(entity.getResumeEntity()) ? ResumeMapper.toDomain(entity.getResumeEntity()) : null)
                .build();
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        return UserEntity.builder()
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
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .gender(domain.getGender())
                .nationality(domain.getNationality())
                .language(domain.getLanguage())
                .birth(domain.getBirth())
                .visa(domain.getVisa())
                .bookMarkEntities(domain.getBookMarks() != null && !domain.getBookMarks().isEmpty() ? BookMarkMapper.toEntities(domain.getBookMarks()) : null)
                .userOwnerJobPostingEntities(domain.getUserOwnerJobPostings() != null && !domain.getUserOwnerJobPostings().isEmpty() ? UserOwnerJobPostingMapper.toEntities(domain.getUserOwnerJobPostings()) : null)
                .resumeEntity(domain.getResume() != null ? ResumeMapper.toEntity(domain.getResume()) : null)
                .build();
    }

    private static boolean isInitialized(Collection<?> collection) {
        return collection instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) collection).wasInitialized();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
