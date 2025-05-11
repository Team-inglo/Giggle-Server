package com.inglo.giggle.owner.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

public record UpdateOwnerRequestDto(

        @JsonProperty("owner_info")
        OwnerInfo ownerInfo,

        @JsonProperty("address")
        AddressRequestDto address,

        @JsonProperty("is_icon_img_changed")
        Boolean isIconImgChanged
) {

    public record OwnerInfo(
            @JsonProperty("company_name")
            String companyName,

            @JsonProperty("owner_name")
            String ownerName,

            @JsonProperty("company_registration_number")
            String companyRegistrationNumber,

            @JsonProperty("phone_number")
            String phoneNumber
    ) {}
}
