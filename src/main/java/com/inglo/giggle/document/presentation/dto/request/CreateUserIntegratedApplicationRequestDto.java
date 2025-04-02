package com.inglo.giggle.document.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateUserIntegratedApplicationRequestDto(

        @NotNull(message = "이름을 입력해주세요.")
        @Size(min = 1, max = 50, message = "first name은 50자 이하로 입력해주세요.")
        @JsonProperty("first_name")
        String firstName,

        @NotNull(message = "성을 입력해주세요.")
        @Size(min = 1, max = 100, message = "last name은 100자 이하로 입력해주세요.")
        @JsonProperty("last_name")
        String lastName,

        @NotNull(message = "생년월일을 입력해주세요.")
        @JsonProperty("birth")
        LocalDate birth,

        @NotNull(message = "성별을 입력해주세요.")
        @JsonProperty("gender")
        String gender,

        @NotNull(message = "국적을 입력해주세요.")
        @Size(min = 1, max = 56, message = "국적은 56자 이하로 입력해주세요.")
        @JsonProperty("nationality")
        String nationality,

        @NotNull(message = "telephone number를 입력해주세요.")
        @Size(min = 10, max = 20, message = "telephone number는 20자 이하로 입력해주세요.")
        @JsonProperty("tele_phone_number")
        String telePhoneNumber,

        @NotNull(message = "cell phone number를 입력해주세요.")
        @Size(min = 10, max = 20, message = "cell phone number는 20자 이하로 입력해주세요.")
        @JsonProperty("cell_phone_number")
        String cellPhoneNumber,

        @NotNull(message = "인증 대학 여부를 입력해주세요.")
        @JsonProperty("is_accredited")
        Boolean isAccredited,

        @NotNull(message = "학교명을 입력해주세요.")
        @Size(min = 1, max = 100, message = "학교명은 100자 이하로 입력해주세요.")
        @JsonProperty("school_name")
        String schoolName,

        @NotNull(message = "학교 전화번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "학교 전화번호는 20자 이하로 입력해주세요.")
        @JsonProperty("school_phone_number")
        String schoolPhoneNumber,

        @NotNull(message = "새 근무지 명을 입력해주세요.")
        @Size(min = 1, max = 50, message = "새 근무지 명은 50자 이하로 입력해주세요.")
        @JsonProperty("new_work_place_name")
        String newWorkPlaceName,

        @NotNull(message = "새 근무지 사업자 등록번호를 입력해주세요.")
        @Size(min = 1, max = 12, message = "새 근무지 사업자 등록번호는 12자 이하로 입력해주세요.")
        @JsonProperty("new_work_place_registration_number")
        String newWorkPlaceRegistrationNumber,

        @NotNull(message = "새 근무지 전화번호를 입력해주세요.")
        @Size(min = 10, max = 20, message = "새 근무지 전화번호는 20자 이하로 입력해주세요.")
        @JsonProperty("new_work_place_phone_number")
        String newWorkPlacePhoneNumber,

        @NotNull(message = "연봉을 입력해주세요.")
        @JsonProperty("annual_income_amount")
        @Max(value = 2000000000, message = "연봉은 2000,000,000원 이하로 입력해주세요.")
        Integer annualIncomeAmount,

        @NotNull(message = "현재 직업을 입력해주세요.")
        @Size(min = 1, max = 30, message = "현재 직업은 30자 이하로 입력해주세요.")
        @JsonProperty("occupation")
        String occupation,

        @NotNull(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞게 입력해주세요.")
        @Size(max = 320, message = "이메일은 320자 이하로 입력해주세요.")
        @JsonProperty("email")
        String email,

        @NotNull(message = "서명을 입력해주세요.")
        @JsonProperty("signature_base64")
        String signatureBase64,

        @NotNull(message = "주소를 입력해주세요.")
        @JsonProperty("address")
        @Valid
        AddressRequestDto address

) {
}
