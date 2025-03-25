package com.inglo.giggle.security.domain.mysql;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.term.domain.TermAccount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@DynamicUpdate
@SQLDelete(sql = "UPDATE accounts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public abstract class Account extends BaseEntity {

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

    @Column(name = "serial_id", length = 320, nullable = false, updatable = false)
    private String serialId;

    @Column(name = "password", length = 320, nullable = false)
    private String password;

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
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TermAccount> termAccounts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountDevice> accountDevices;

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
