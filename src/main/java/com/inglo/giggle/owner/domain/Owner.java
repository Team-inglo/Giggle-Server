package com.inglo.giggle.owner.domain;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Owner extends BaseDomain {
    private UUID id;
    private String email;
    private String profileImgUrl;
    private String phoneNumber;
    private Boolean marketingAllowed;
    private Boolean notificationAllowed;
    private String companyName;
    private String ownerName;
    private String companyRegistrationNumber;
    private Address address;

    @Builder
    public Owner(UUID id,
                 String email, String profileImgUrl, String phoneNumber,
                 Boolean marketingAllowed, Boolean notificationAllowed,
                 String companyName, String ownerName, String companyRegistrationNumber,
                 LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                 Address address
    ) {
        this.id = id;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public ESecurityRole getRole() {
        return ESecurityRole.OWNER;
    }

    public String getName() {
        return this.companyName;
    }

    public void validateOwnerOwn(UUID ownerId) {
        if (! this.id.equals(ownerId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void updateSelf(
            String phoneNumber,
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            Address address
    ) {
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.address = address;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateNotificationAllowed(Boolean notificationAllowed) {
        this.notificationAllowed = notificationAllowed;
    }
}

