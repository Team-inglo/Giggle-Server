package com.inglo.giggle.account.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerDetailResponseDto extends SelfValidating<ReadOwnerDetailResponseDto> {

    @NotBlank(message = "company_name은 null일 수 없습니다.")
    @JsonProperty("company_name")
    private final String companyName;

    @NotBlank(message = "owner_name은 null일 수 없습니다.")
    @JsonProperty("owner_name")
    private final String ownerName;

    @NotNull(message = "address는 null일 수 없습니다.")
    @JsonProperty("address")
    private final AddressDto address;

    @NotBlank(message = "company_registration_number는 null일 수 없습니다.")
    @JsonProperty("company_registration_number")
    private final String companyRegistrationNumber;

    @NotBlank(message = "phone_number는 null일 수 없습니다.")
    @JsonProperty("phone_number")
    private final String phoneNumber;

    @NotBlank(message = "logo_img_url은 null일 수 없습니다.")
    @JsonProperty("logo_img_url")
    private final String logoImgUrl;

    @Builder
    public ReadOwnerDetailResponseDto(
            String companyName,
            String ownerName,
            AddressDto address,
            String companyRegistrationNumber,
            String phoneNumber,
            String logoImgUrl
    ) {
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.address = address;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.phoneNumber = phoneNumber;
        this.logoImgUrl = logoImgUrl;

        this.validateSelf();
    }

    public static ReadOwnerDetailResponseDto fromEntity(Owner owner) {
        return ReadOwnerDetailResponseDto.builder()
                .companyName(owner.getCompanyName())
                .ownerName(owner.getOwnerName())
                .address(AddressDto.fromEntity(owner.getAddress()))
                .companyRegistrationNumber(owner.getCompanyRegistrationNumber())
                .phoneNumber(owner.getPhoneNumber())
                .logoImgUrl(owner.getProfileImgUrl())
                .build();
    }

    @Getter
    public static class AddressDto {

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

        @NotBlank(message = "region_4depth_name은 null일 수 없습니다.")
        @JsonProperty("region_4depth_name")
        private final String region4depthName;

        @NotBlank(message = "address_detail은 null일 수 없습니다.")
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
}
