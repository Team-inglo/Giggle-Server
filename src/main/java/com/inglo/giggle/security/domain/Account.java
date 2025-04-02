package com.inglo.giggle.security.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.term.domain.TermAccount;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class Account extends BaseDomain {
    protected UUID id;
    protected ESecurityProvider provider;
    protected String serialId;
    protected String password;
    protected String email;
    protected String profileImgUrl;
    protected String phoneNumber;
    protected Boolean marketingAllowed;
    protected Boolean notificationAllowed;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    protected List<TermAccount> termAccounts;
    protected List<AccountDevice> accountDevices;

    protected Account(
            UUID id,
            ESecurityProvider provider,
            String serialId,
            String password,
            String email,
            String profileImgUrl,
            String phoneNumber,
            Boolean marketingAllowed,
            Boolean notificationAllowed,
            LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,

            /* -------------------------------------------- */
            /* One To Many Mapping ------------------------ */
            /* -------------------------------------------- */
            List<TermAccount> termAccounts,
            List<AccountDevice> accountDevices
    ) {
        this.id = id;
        this.provider = provider;
        this.serialId = serialId;
        this.password = password;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.termAccounts = termAccounts;
        this.accountDevices = accountDevices;
    }

    public abstract ESecurityRole getRole();

    public abstract String getName();

    public void checkUserValidation() {
        if (!this.getRole().equals(ESecurityRole.USER))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }

    public void checkOwnerValidation() {
        if (!this.getRole().equals(ESecurityRole.OWNER))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }

    public void checkAdminValidation() {
        if (!this.getRole().equals(ESecurityRole.ADMIN))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateAccountDevices(List<AccountDevice> accountDevices) {
        this.accountDevices = accountDevices;
    }

    public void updateNotificationAllowed(Boolean notificationAllowed) {
        this.notificationAllowed = notificationAllowed;
    }
}

