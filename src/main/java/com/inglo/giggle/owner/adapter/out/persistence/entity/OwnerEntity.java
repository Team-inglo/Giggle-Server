package com.inglo.giggle.owner.adapter.out.persistence.entity;

import com.inglo.giggle.core.persistence.AddressEntity;
import com.inglo.giggle.core.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "owners")
@DynamicUpdate
@SQLDelete(sql = "UPDATE owners SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class OwnerEntity extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

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

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Column(name = "owner_name", length = 10, nullable = false)
    private String ownerName;

    @Column(name = "company_registration_number", length = 12, nullable = false)
    private String companyRegistrationNumber;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private AddressEntity addressEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public OwnerEntity(
            UUID id,
            String email,
            String profileImgUrl,
            String phoneNumber,
            Boolean notificationAllowed,
            Boolean marketingAllowed,
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            AddressEntity addressEntity
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
        this.addressEntity = addressEntity;
    }
}
