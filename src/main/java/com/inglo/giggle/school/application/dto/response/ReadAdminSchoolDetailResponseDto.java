package com.inglo.giggle.school.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.domain.Address;
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
    private final AddressDto address;

    @Builder
    public ReadAdminSchoolDetailResponseDto(
            Long id,
            String schoolName,
            String schoolPhoneNumber,
            Boolean isMetropolitan,
            String instituteName,
            String coordinatorName,
            String coordinatorPhoneNumber,
            AddressDto address
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

    @Getter
    public static class AddressDto extends SelfValidating<AddressDto> {

        @NotBlank(message = "address_name은 null일 수 없습니다.")
        @JsonProperty("address_name")
        private final String addressName;

        @NotBlank(message = "region_1depth_name은 null일 수 없습니다.")
        @JsonProperty("region_1depth_name")
        private final String region1depthName;

        @NotBlank(message = "region_2depth_name은 null일 수 없습니다.")
        @JsonProperty("region_2depth_name")
        private final String region2depthName;

        @NotBlank(message = "region_3depth_name은 null일 수 없습니다.")
        @JsonProperty("region_3depth_name")
        private final String region3depthName;

        @JsonProperty("region_4depth_name")
        private final String region4depthName;

        @JsonProperty("address_detail")
        private final String addressDetail;

        @NotNull(message = "longitude는 null일 수 없습니다.")
        @JsonProperty("longitude")
        private final Double longitude;

        @NotNull(message = "latitude는 null일 수 없습니다.")
        @JsonProperty("latitude")
        private final Double latitude;

        @Builder
        public AddressDto(
                String addressName,
                String region1depthName,
                String region2depthName,
                String region3depthName,
                String region4depthName,
                String addressDetail,
                Double longitude,
                Double latitude
        ) {
            this.addressName = addressName;
            this.region1depthName = region1depthName;
            this.region2depthName = region2depthName;
            this.region3depthName = region3depthName;
            this.region4depthName = region4depthName;
            this.addressDetail = addressDetail;
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public static AddressDto fromEntity(Address address) {
            return AddressDto.builder()
                    .addressName(address.getAddressName())
                    .region1depthName(address.getRegion1DepthName())
                    .region2depthName(address.getRegion2DepthName())
                    .region3depthName(address.getRegion3DepthName())
                    .region4depthName(address.getRegion4DepthName())
                    .addressDetail(address.getAddressDetail())
                    .longitude(address.getLongitude())
                    .latitude(address.getLatitude())
                    .build();
        }
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
                .address(AddressDto.fromEntity(school.getAddress()))
                .build();
    }
}
