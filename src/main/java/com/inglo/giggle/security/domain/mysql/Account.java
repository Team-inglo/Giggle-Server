package com.inglo.giggle.security.domain.mysql;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_accounts_serial_id",
                        columnNames = {"serial_id"}
                )
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@DynamicUpdate
public abstract class Account {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    /* -------------------------------------------- */
    /* Security Column ---------------------------- */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    private ESecurityProvider provider;

    @Column(name = "serial_id", length = 20, nullable = false, updatable = false)
    private String serialId;

    @Column(name = "password", length = 320, nullable = false)
    private String password;

//    TODO: Notification 기능 추가 시, 추가
//    @Column(name = "device_token", length = 320)
//    private String deviceToken;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Column(name = "profile_img_url", length = 320, nullable = false)
    private String profileImgUrl;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "marketing_allowed", nullable = false)
    private Boolean marketingAllowed;

    @Column(name = "notification_allowed", nullable = false)
    private Boolean notificationAllowed;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    public Account(
            ESecurityProvider provider,
            String serialId,
            String password,
            String email,
            String profileImgUrl,
            String phoneNumber,
            Boolean marketingAllowed,
            Boolean notificationAllowed
    ) {
        this.provider = provider;
        this.serialId = serialId;
        this.password = password;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
    }

    public void updatePassword(String password) {
        this.password = password;
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

    public abstract ESecurityRole getRole();
    public abstract String getName();
}
