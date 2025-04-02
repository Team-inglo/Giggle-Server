package com.inglo.giggle.document.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserStandardLaborContractRequestDto(

        @NotNull(message = "first name 을 입력해주세요.")
        @Size(min = 1, max = 50, message = "first name은 50자 이하로 입력해주세요.")
        @JsonProperty("first_name")
        String firstName,

        @NotNull(message = "last name 을 입력해주세요.")
        @Size(min = 1, max = 100, message = "last name은 100자 이하로 입력해주세요.")
        @JsonProperty("last_name")
        String lastName,

        @NotNull(message = "주소를 입력해주세요.")
        @JsonProperty("address")
        @Valid
        AddressRequestDto address,

        @NotNull(message = "전화번호를 입력해주세요.")
        @Size(min = 10, max = 20, message = "전화번호는 10자 이상, 20자 이하로 입력해주세요.")
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull(message = "서명을 입력해주세요.")
        @JsonProperty("signature_base64")
        String signatureBase64
) {}
