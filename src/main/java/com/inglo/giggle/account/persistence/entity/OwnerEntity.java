package com.inglo.giggle.account.persistence.entity;

import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;
import com.inglo.giggle.security.persistence.entity.mysql.AccountDeviceEntity;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.term.persistence.entity.TermAccountEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

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
@OnDelete(action = OnDeleteAction.CASCADE)
public class OwnerEntity extends AccountEntity {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
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
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "ownerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobPostingEntity> jobPostingEntities;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public OwnerEntity(
            ESecurityProvider provider,
            String serialId,
            String password,
            String email,
            String profileImgUrl,
            String phoneNumber,
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            AddressEntity addressEntity,
            Boolean notificationAllowed,
            Boolean marketingAllowed,
            List<TermAccountEntity> termAccountEntities,
            List<AccountDeviceEntity> accountDeviceEntities,
            List<JobPostingEntity> jobPostingEntities
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
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.addressEntity = addressEntity;
        this.jobPostingEntities = jobPostingEntities;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.OWNER;
    }
    @Override
    public String getName() {
        return this.companyName;
    }
}
