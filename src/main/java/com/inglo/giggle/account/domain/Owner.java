package com.inglo.giggle.account.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Owner extends Account {
    private String companyName;
    private String ownerName;
    private String companyRegistrationNumber;
    private Address address;

    @Builder
    public Owner(UUID id, ESecurityProvider provider, String serialId, String password,
                 String email, String profileImgUrl, String phoneNumber,
                 Boolean marketingAllowed, Boolean notificationAllowed,
                 String companyName, String ownerName, String companyRegistrationNumber,
                 LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                 Address address
    ) {
        super(
                id,
                provider,
                serialId,
                password,
                email,
                profileImgUrl,
                phoneNumber,
                marketingAllowed,
                notificationAllowed,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.address = address;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.OWNER;
    }

    @Override
    public String getName() {
        return this.companyName;
    }

    public void validateOwnerOwn(UUID ownerId) {
        if (! this.id.equals(ownerId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void updateSelf(
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            Address address
    ) {
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.address = address;
    }
}

