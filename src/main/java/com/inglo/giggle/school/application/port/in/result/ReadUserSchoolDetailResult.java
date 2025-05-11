package com.inglo.giggle.school.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserSchoolDetailResult extends SelfValidating<ReadUserSchoolDetailResult> {

    @JsonProperty("school_name")
    @NotBlank(message = "school_name은 null일 수 없습니다.")
    private final String schoolName;

    @JsonProperty("institute_name")
    private final String instituteName;

    @JsonProperty("coordinator_name")
    private final String coordinatorName;

    @JsonProperty("coordinator_phone_number")
    private final String coordinatorPhoneNumber;

    @JsonProperty("address")
    AddressResponseDto address;

    @JsonProperty("is_metropolitan_area")
    @NotNull(message = "is_metropolitan_area는 null일 수 없습니다.")
    private final Boolean isMetropolitanArea;

    @Builder
    public ReadUserSchoolDetailResult(
            String schoolName,
            String instituteName,
            String coordinatorName,
            String coordinatorPhoneNumber,
            AddressResponseDto address,
            Boolean isMetropolitanArea
    ) {
        this.schoolName = schoolName;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.address = address;
        this.isMetropolitanArea = isMetropolitanArea;

        this.validateSelf();
    }

    public static ReadUserSchoolDetailResult of(
            String schoolName,
            String instituteName,
            String coordinatorName,
            String coordinatorPhoneNumber,
            AddressResponseDto address,
            Boolean isMetropolitanArea
    ) {
        return ReadUserSchoolDetailResult.builder()
                .schoolName(schoolName)
                .instituteName(instituteName)
                .coordinatorName(coordinatorName)
                .coordinatorPhoneNumber(coordinatorPhoneNumber)
                .address(address)
                .isMetropolitanArea(isMetropolitanArea)
                .build();
    }
}
