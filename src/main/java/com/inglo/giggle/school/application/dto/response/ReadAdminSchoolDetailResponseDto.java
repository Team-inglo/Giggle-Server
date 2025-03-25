package com.inglo.giggle.school.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.response.AddressResponseDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.school.domain.School;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminSchoolDetailResponseDto extends SelfValidating<ReadAdminSchoolDetailResponseDto> {

    @JsonProperty("id")
    @NotNull(message = "아이디는 필수입니다.")
    private final Long id;

    @JsonProperty("school_name")
    @NotBlank(message = "학교 이름은 필수입니다.")
    private final String schoolName;

    @JsonProperty("school_phone_number")
    private final String schoolPhoneNumber;

    @JsonProperty("is_metropolitan")
    private final Boolean isMetropolitan;

    @JsonProperty("institute_name")
    private final String instituteName;

    @JsonProperty("coordinator_name")
    private final String coordinatorName;

    @JsonProperty("coordinator_phone_number")
    private final String coordinatorPhoneNumber;

    @JsonProperty("address")
    private final AddressResponseDto address;

    @Builder
    public ReadAdminSchoolDetailResponseDto(
            Long id,
            String schoolName,
            String schoolPhoneNumber,
            Boolean isMetropolitan,
            String instituteName,
            String coordinatorName,
            String coordinatorPhoneNumber,
            AddressResponseDto address
    ) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.isMetropolitan = isMetropolitan;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.address = address;

        this.validateSelf();
    }

    public static ReadAdminSchoolDetailResponseDto of(School school) {
        return ReadAdminSchoolDetailResponseDto.builder()
                .id(school.getId())
                .schoolName(school.getSchoolName())
                .schoolPhoneNumber(school.getSchoolPhoneNumber())
                .isMetropolitan(school.getIsMetropolitan())
                .instituteName(school.getInstituteName())
                .coordinatorName(school.getCoordinatorName())
                .coordinatorPhoneNumber(school.getCoordinatorPhoneNumber())
                .address(AddressResponseDto.fromEntity(school.getAddress()))
                .build();
    }
}
