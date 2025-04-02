package com.inglo.giggle.security.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AccountDevice extends BaseDomain {
    private Long id;
    private UUID deviceId;
    private String deviceToken;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID accountId;

    @Builder
    public AccountDevice(Long id, UUID deviceId, String deviceToken, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, UUID accountId) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.accountId = accountId;
    }

    public void updateSelf(UUID deviceId, String deviceToken) {
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
    }
}

