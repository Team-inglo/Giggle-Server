package com.inglo.giggle.account.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
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

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private Address address;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobPosting> jobPostings;

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
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.address = address;
    }

    public void updateCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void updateOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void updateCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public void updateAddress(Address address) {
        this.address = address;
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
