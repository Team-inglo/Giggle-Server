package com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql;

import com.inglo.giggle.core.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class AccountDeviceEntity extends BaseEntity {

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
    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity accountEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public AccountDeviceEntity(
            Long id,
            UUID deviceId,
            String deviceToken,
            AccountEntity accountEntity
    ) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
        this.accountEntity = accountEntity;
    }
}
