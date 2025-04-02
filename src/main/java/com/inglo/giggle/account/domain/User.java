package com.inglo.giggle.account.domain;

import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.AccountDevice;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.term.domain.TermAccount;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class User extends Account {
    private String firstName;
    private String lastName;
    private EGender gender;
    private String nationality;
    private ELanguage language;
    private LocalDate birth;
    private EVisa visa;
    private Address address;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    private List<BookMark> bookMarks;
    private List<UserOwnerJobPosting> userOwnerJobPostings;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    private Resume resume;


    @Builder
    public User(UUID id, ESecurityProvider provider, String serialId, String password,
                String email, String profileImgUrl, String phoneNumber,
                Boolean marketingAllowed, Boolean notificationAllowed,
                String firstName, String lastName, EGender gender,
                String nationality, ELanguage language, LocalDate birth,
                EVisa visa, Address address,
                LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                List<TermAccount> termAccounts, List<AccountDevice> accountDevices,
                List<BookMark> bookMarks, List<UserOwnerJobPosting> userOwnerJobPostings, Resume resume) {
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
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.language = language;
        this.birth = birth;
        this.visa = visa;
        this.address = address;
        this.bookMarks = bookMarks;
        this.userOwnerJobPostings = userOwnerJobPostings;
        this.resume = resume;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }

    @Override
    public String getName() {
        return this.firstName + "-" + this.lastName;
    }

    public void updateLanguage(ELanguage language) {
        this.language = language;
    }
    public void updateSelf(
            String firstName,
            String lastName,
            EGender gender,
            String nationality,
            EVisa visa,
            LocalDate birth,
            Address address
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.visa = visa;
        this.birth = birth;
        this.address = address;
    }
}
