package com.inglo.giggle.user.adapter.out.persistence.entity;

import com.inglo.giggle.core.persistence.AddressEntity;
import com.inglo.giggle.core.persistence.BaseEntity;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.user.domain.type.ELanguage;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@DynamicUpdate
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class UserEntity extends BaseEntity {

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

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private EGender gender;

    @Column(name = "nationality", length = 56)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private ELanguage language;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "visa")
    private EVisa visa;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private AddressEntity addressEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public UserEntity(
            UUID id,
            String email,
            String profileImgUrl,
            String phoneNumber,
            Boolean notificationAllowed,
            Boolean marketingAllowed,
            String firstName,
            String lastName,
            EGender gender,
            String nationality,
            ELanguage language,
            LocalDate birth,
            EVisa visa,
            AddressEntity addressEntity
    ) {
        this.id = id;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.language = language;
        this.birth = birth;
        this.visa = visa;
        this.addressEntity = addressEntity;
    }
}

