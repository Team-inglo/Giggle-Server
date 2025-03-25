package com.inglo.giggle.account.domain;

import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@OnDelete(action = OnDeleteAction.CASCADE)
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
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserOwnerJobPosting> userOwnerJobPostings = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Resume resume;

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
            Address address,
            Boolean notificationAllowed,
            Boolean marketingAllowed
    ) {
        super(
                provider,
                serialId,
                password,
                email,
                profileImgUrl,
                phoneNumber,
                notificationAllowed,
                marketingAllowed
        );
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.language = language;
        this.birth = birth;
        this.visa = visa;
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

    public void updateLanguage(ELanguage language) {
        this.language = language;
    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }
    @Override
    public String getName() {
        return this.firstName + "-" + this.lastName;
    }
}

