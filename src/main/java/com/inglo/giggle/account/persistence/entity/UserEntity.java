package com.inglo.giggle.account.persistence.entity;

import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.persistence.entity.BookMarkEntity;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.persistence.entity.mysql.AccountDeviceEntity;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.term.persistence.entity.TermAccountEntity;
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
public class UserEntity extends AccountEntity {

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
    private AddressEntity addressEntity;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkEntity> bookMarkEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<UserOwnerJobPostingEntity> userOwnerJobPostingEntities = new ArrayList<>();

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private ResumeEntity resumeEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public UserEntity(
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
            AddressEntity addressEntity,
            Boolean notificationAllowed,
            Boolean marketingAllowed,
            List<TermAccountEntity> termAccountEntities,
            List<AccountDeviceEntity> accountDeviceEntities,
            List<BookMarkEntity> bookMarkEntities,
            List<UserOwnerJobPostingEntity> userOwnerJobPostingEntities,
            ResumeEntity resumeEntity
    ) {
        super(
                provider,
                serialId,
                password,
                email,
                profileImgUrl,
                phoneNumber,
                notificationAllowed,
                marketingAllowed,
                termAccountEntities,
                accountDeviceEntities
        );
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.language = language;
        this.birth = birth;
        this.visa = visa;
        this.addressEntity = addressEntity;
        this.bookMarkEntities = bookMarkEntities;
        this.userOwnerJobPostingEntities = userOwnerJobPostingEntities;
        this.resumeEntity = resumeEntity;
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

