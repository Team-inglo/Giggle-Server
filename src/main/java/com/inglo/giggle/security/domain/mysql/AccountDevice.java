package com.inglo.giggle.security.domain.mysql;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "account_devices")
public class AccountDevice {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @NotNull
    @Column(name = "device_token", length = 320)
    private String deviceToken;

    @NotNull
    @Column(name = "device_id")
    private UUID deviceId;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public AccountDevice(
            UUID deviceId,
            String deviceToken,
            Account account
    ) {
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
        this.account = account;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void updateDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }
}
