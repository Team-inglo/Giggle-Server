package com.inglo.giggle.account.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateOwnerRequestDto(

        @NotNull
        @JsonProperty("owner_info")
        OwnerInfo ownerInfo,

        @NotNull
        @JsonProperty("address")
        AddressRequestDto address,

        @NotNull
        @JsonProperty("is_icon_img_changed")
        Boolean isIconImgChanged
) {

    public record OwnerInfo(
            @NotNull
            @Size(min = 1, max = 50)
            @JsonProperty("company_name")
            String companyName,

            @NotNull
            @Size(min = 1, max = 50)
            @JsonProperty("owner_name")
            String ownerName,

            @NotNull
            @Size(min = 1, max = 20)
            @JsonProperty("company_registration_number")
            String companyRegistrationNumber,

            @NotNull
            @Size(min = 10, max = 15)
            @JsonProperty("phone_number")
            String phoneNumber
    ) {}
}
