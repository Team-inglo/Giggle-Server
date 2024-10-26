package com.inglo.giggle.account.domain;

import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.address.domain.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@PrimaryKeyJoinColumn(
        name = "account_id",
        foreignKey = @ForeignKey(name = "fk_user_account")
)
@DiscriminatorValue("USER")
@DynamicUpdate
public class User extends Account {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private EGender gender;

    @Column(name = "nationality", length = 56, nullable = false)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private ELanguage language;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "visa")
    private EVisa visa;

    @Column(name = "marketing_allowed")
    private Boolean marketingAllowed;

    @Column(name = "notification_allowed")
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
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public User(
            ESecurityProvider provider,
            String serialId,
            String password,
            String email,
            String profileImgUrl,
            String phoneNumber,
            String firstName,
            String lastName,
            EGender gender,
            String nationality,
            ELanguage language,
            LocalDate birth,
            EVisa visa,
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

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.language = language;
        this.birth = birth;
        this.visa = visa;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.createdAt = LocalDateTime.now();
        this.address = address;
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    public void updateGender(EGender gender) {
        this.gender = gender;
    }

    public void updateNationality(String nationality) {
        this.nationality = nationality;
    }

    public void updateVisa(EVisa visa) {
        this.visa = visa;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }
    @Override
    public String getName() {
        return this.firstName + " " + this.lastName;
    }
}

