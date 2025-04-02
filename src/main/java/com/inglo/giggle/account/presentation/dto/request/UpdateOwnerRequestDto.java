package com.inglo.giggle.account.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateOwnerRequestDto(

        @NotNull
        @JsonProperty("owner_info")
        @Valid
        OwnerInfo ownerInfo,

        @NotNull
        @JsonProperty("address")
        @Valid
        AddressRequestDto address,

        @NotNull
        @JsonProperty("is_icon_img_changed")
        Boolean isIconImgChanged
) {

    public record OwnerInfo(
            @NotNull
            @Size(min = 1, max = 100)
            @JsonProperty("company_name")
            String companyName,

            @NotNull
            @Size(min = 1, max = 10)
            @JsonProperty("owner_name")
            String ownerName,

            @NotNull
            @Size(min = 1, max = 12)
            @JsonProperty("company_registration_number")
            String companyRegistrationNumber,

            @NotNull
            @Size(min = 10, max = 20)
            @JsonProperty("phone_number")
            String phoneNumber
    ) {}
}
