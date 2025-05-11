package com.inglo.giggle.security.account.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Account extends BaseDomain {
    private UUID id;
    private ESecurityProvider provider;
    private ESecurityRole role;
    private String serialId;
    private String password;
    private RefreshToken refreshToken;
    private List<AccountDevice> accountDevices;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Account(
            UUID id,
            ESecurityProvider provider,
            ESecurityRole role,
            String serialId,
            String password,
            RefreshToken refreshToken,
            List<AccountDevice> accountDevices,
            LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        this.id = id;
        this.provider = provider;
        this.role = role;
        this.serialId = serialId;
        this.password = password;
        this.refreshToken = refreshToken;
        this.accountDevices = accountDevices;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
    public void updateAccountDevices(List<AccountDevice> accountDevices) {
        this.accountDevices = accountDevices;
    }

    public void deleteAllAccountDevices() {
        this.accountDevices.clear();
    }
}

