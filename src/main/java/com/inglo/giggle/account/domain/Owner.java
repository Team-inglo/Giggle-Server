package com.inglo.giggle.account.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "owners")
@PrimaryKeyJoinColumn(
        name = "account_id",
        foreignKey = @ForeignKey(name = "fk_owner_account")
)
@DiscriminatorValue("OWNER")
@DynamicUpdate
public class Owner extends Account {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Column(name = "owner_name", length = 10, nullable = false)
    private String ownerName;

    @Column(name = "company_registration_number", length = 12, nullable = false)
    private String companyRegistrationNumber;

    @Column(name = "marketing_allowed", nullable = false)
    private Boolean marketingAllowed;

    @Column(name = "notification_allowed", nullable = false)
    private Boolean notificationAllowed;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private Address address;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Owner(
            ESecurityProvider provider,
            String serialId,
            String password,
            String email,
            String profileImgUrl,
            String phoneNumber,
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            Boolean marketingAllowed,
            Boolean notificationAllowed,
            Address address
    ) {
        super(
                provider,
                serialId,
                password,
                email,
                profileImgUrl,
                phoneNumber
        );
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.createdAt = LocalDateTime.now();
        this.address = address;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }
    @Override
    public String getName() {
        return this.companyName;
    }
}
