package com.inglo.giggle.account.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.AccountDevice;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.term.domain.TermAccount;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Owner extends Account {
    private String companyName;
    private String ownerName;
    private String companyRegistrationNumber;
    private Address address;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    private List<JobPosting> jobPostings;

    @Builder
    public Owner(UUID id, ESecurityProvider provider, String serialId, String password,
                 String email, String profileImgUrl, String phoneNumber,
                 Boolean marketingAllowed, Boolean notificationAllowed,
                 String companyName, String ownerName, String companyRegistrationNumber,
                 LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                 Address address, List<TermAccount> termAccounts, List<AccountDevice> accountDevices,
                 List<JobPosting> jobPostings
    ) {
        super(
                id,
                provider,
                serialId,
                password,
                email,
                profileImgUrl,
                phoneNumber,
                marketingAllowed,
                notificationAllowed,
                createdAt,
                updatedAt,
                deletedAt,
                termAccounts,
                accountDevices
        );
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.address = address;
        this.jobPostings = jobPostings;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.OWNER;
    }

    @Override
    public String getName() {
        return this.companyName;
    }

    public void updateSelf(
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            Address address
    ) {
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.address = address;
    }
}

