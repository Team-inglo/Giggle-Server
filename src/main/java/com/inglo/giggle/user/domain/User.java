package com.inglo.giggle.user.domain;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.user.domain.type.ELanguage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User extends BaseDomain {
    private UUID id;
    private String email;
    private String profileImgUrl;
    private String phoneNumber;
    private Boolean marketingAllowed;
    private Boolean notificationAllowed;
    private String firstName;
    private String lastName;
    private EGender gender;
    private String nationality;
    private ELanguage language;
    private LocalDate birth;
    private EVisa visa;
    private Address address;

    @Builder
    public User(UUID id,
                String email, String profileImgUrl, String phoneNumber,
                Boolean marketingAllowed, Boolean notificationAllowed,
                String firstName, String lastName, EGender gender,
                String nationality, ELanguage language, LocalDate birth,
                EVisa visa, Address address,
                LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
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
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }

    public String getName() {
        return this.firstName + "-" + this.lastName;
    }

    public void updateLanguage(ELanguage language) {
        this.language = language;
    }
    public void updateSelf(
            String phoneNumber,
            String firstName,
            String lastName,
            EGender gender,
            String nationality,
            EVisa visa,
            LocalDate birth,
            Address address
    ) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.visa = visa;
        this.birth = birth;
        this.address = address;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateNotificationAllowed(Boolean notificationAllowed) {
        this.notificationAllowed = notificationAllowed;
    }
}
