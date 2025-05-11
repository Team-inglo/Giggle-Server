package com.inglo.giggle.owner.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerDetailResult extends SelfValidating<ReadOwnerDetailResult> {

    @NotBlank(message = "company_name은 null일 수 없습니다.")
    @JsonProperty("company_name")
    private final String companyName;

    @NotBlank(message = "owner_name은 null일 수 없습니다.")
    @JsonProperty("owner_name")
    private final String ownerName;

    @NotNull(message = "address는 null일 수 없습니다.")
    @JsonProperty("address")
    private final AddressResponseDto address;

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
    public ReadOwnerDetailResult(
            String companyName,
            String ownerName,
            AddressResponseDto address,
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

    public static ReadOwnerDetailResult of(
            String companyName,
            String ownerName,
            AddressResponseDto address,
            String companyRegistrationNumber,
            String phoneNumber,
            String logoImgUrl
    ) {
        return ReadOwnerDetailResult.builder()
                .companyName(companyName)
                .ownerName(ownerName)
                .address(address)
                .companyRegistrationNumber(companyRegistrationNumber)
                .phoneNumber(phoneNumber)
                .logoImgUrl(logoImgUrl)
                .build();
    }
}
