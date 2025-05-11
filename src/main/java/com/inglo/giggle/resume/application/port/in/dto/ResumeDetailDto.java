package com.inglo.giggle.resume.application.port.in.dto;

import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class ResumeDetailDto {

    private final UUID accountId;
    private final String introduction;
    private final String profileImgUrl;
    private final String name;
    private final EVisa visa;
    private final EGender gender;
    private final String nationality;
    private final String birth;
    private final String mainAddress;
    private final String detailAddress;
    private final String phoneNumber;
    private final String email;
    private final List<EducationWithSchoolNameDto> educationWithSchoolNames;


    public ResumeDetailDto(
            UUID accountId,
            String introduction,
            String profileImgUrl,
            String name,
            EVisa visa,
            EGender gender,
            String nationality,
            String birth,
            String mainAddress,
            String detailAddress,
            String phoneNumber,
            String email,
            List<EducationWithSchoolNameDto> educationWithSchoolNames

    ) {
        this.accountId = accountId;
        this.introduction = introduction;
        this.profileImgUrl = profileImgUrl;
        this.name = name;
        this.visa = visa;
        this.gender = gender;
        this.nationality = nationality;
        this.birth = birth;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.educationWithSchoolNames = educationWithSchoolNames;
    }
}
