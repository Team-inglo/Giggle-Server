package com.inglo.giggle.school.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.school.domain.School;
import lombok.Builder;

public class ReadUserSchoolDetailResponseDto extends SelfValidating<ReadUserSchoolDetailResponseDto> {
    @JsonProperty("school_name")
    private final String schoolName;

    @JsonProperty("institute_name")
    private final String instituteName;

    @JsonProperty("coordinator_name")
    private final String coordinatorName;

    @JsonProperty("coordinator_phone_number")
    private final String coordinatorPhoneNumber;

    @JsonProperty("address")
    AddressRequestDto address;

    @Builder
    public ReadUserSchoolDetailResponseDto(String schoolName, String instituteName, String coordinatorName, String coordinatorPhoneNumber, AddressRequestDto address) {
        this.schoolName = schoolName;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.address = address;

        this.validateSelf();
    }

    public static ReadUserSchoolDetailResponseDto of(Education education) {
        if (education == null) {
            return ReadUserSchoolDetailResponseDto.builder()
                    .schoolName(null)
                    .instituteName(null)
                    .coordinatorName(null)
                    .coordinatorPhoneNumber(null)
                    .address(null)
                    .build();
        }
        return ReadUserSchoolDetailResponseDto.builder()
                .schoolName(education.getSchool().getSchoolName())
                .instituteName(education.getSchool().getInstituteName())
                .coordinatorName(education.getSchool().getCoordinatorName())
                .coordinatorPhoneNumber(education.getSchool().getCoordinatorPhoneNumber())
                .address(AddressRequestDto.fromEntity(education.getSchool().getAddress()))
                .build();
    }
}
