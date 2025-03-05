package com.inglo.giggle.account.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserDetailResponseDto extends SelfValidating<ReadUserDetailResponseDto> {

    @NotNull
    @JsonProperty("profile_img_url")
    private String profileImgUrl;

    @NotBlank(message = "first_name은 null일 수 없습니다.")
    @JsonProperty("first_name")
    private final String firstName;

    @NotBlank(message = "last_name은 null일 수 없습니다.")
    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("birth")
    private final String birth;

    @NotBlank(message = "gender는 null일 수 없습니다.")
    @JsonProperty("gender")
    private final String gender;

    @JsonProperty("nationality")
    private final String nationality;

    @NotNull(message = "visa는 null일 수 없습니다.")
    @JsonProperty("visa")
    private final String visa;

    @NotBlank(message = "phone_number는 null일 수 없습니다.")
    @JsonProperty("phone_number")
    private final String phoneNumber;

    @JsonProperty("address")
    private final AddressDto address;

    @Builder
    public ReadUserDetailResponseDto(
            String profileImgUrl,
            String firstName,
            String lastName,
            String birth,
            String gender,
            String nationality,
            String visa,
            String phoneNumber,
            AddressDto address
    ) {
        this.profileImgUrl = profileImgUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.visa = visa;
        this.phoneNumber = phoneNumber;
        this.address = address;

        this.validateSelf();
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

        public static ReadUserDetailResponseDto.AddressDto fromEntity(Address address) {
            return ReadUserDetailResponseDto.AddressDto.builder()
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

    public static ReadUserDetailResponseDto fromEntity(User user) {
        return ReadUserDetailResponseDto.builder()
                .profileImgUrl(user.getProfileImgUrl())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birth(user.getBirth() != null ? DateTimeUtil.convertLocalDateToString(user.getBirth()) : null)
                .gender(user.getGender().name())
                .nationality(user.getNationality() != null ? user.getNationality() : null)
                .visa(user.getVisa().name())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress() != null ? AddressDto.fromEntity(user.getAddress()) : null)
                .build();
    }
}
