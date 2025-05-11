package com.inglo.giggle.document.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadIntegratedApplicationDetailResult extends SelfValidating<ReadIntegratedApplicationDetailResult> {

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("birth")
    private final String birth;

    @JsonProperty("gender")
    private final String gender;

    @JsonProperty("nationality")
    private final String nationality;

    @JsonProperty("tele_phone_number")
    private final String telePhoneNumber;

    @JsonProperty("cell_phone_number")
    private final String cellPhoneNumber;

    @JsonProperty("is_accredited")
    private final Boolean isAccredited;

    @JsonProperty("school_name")
    private final String schoolName;

    @JsonProperty("school_phone_number")
    private final String schoolPhoneNumber;

    @JsonProperty("new_work_place_name")
    private final String newWorkPlaceName;

    @JsonProperty("new_work_place_registration_number")
    private final String newWorkPlaceRegistrationNumber;

    @JsonProperty("new_work_place_phone_number")
    private final String newWorkPlacePhoneNumber;

    @JsonProperty("annual_income_amount")
    private final Integer annualIncomeAmount;

    @JsonProperty("occupation")
    private final String occupation;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("signature_base64")
    private final String signatureBase64;

    @JsonProperty("address")
    private final AddressResponseDto address;

    @Builder
    public ReadIntegratedApplicationDetailResult(
            String firstName,
            String lastName,
            String birth,
            String gender,
            String nationality,
            String telePhoneNumber,
            String cellPhoneNumber,
            Boolean isAccredited,
            String schoolName,
            String schoolPhoneNumber,
            String newWorkPlaceName,
            String newWorkPlaceRegistrationNumber,
            String newWorkPlacePhoneNumber,
            Integer annualIncomeAmount,
            String occupation,
            String email,
            String signatureBase64,
            AddressResponseDto address
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.telePhoneNumber = telePhoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.isAccredited = isAccredited;
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.newWorkPlaceName = newWorkPlaceName;
        this.newWorkPlaceRegistrationNumber = newWorkPlaceRegistrationNumber;
        this.newWorkPlacePhoneNumber = newWorkPlacePhoneNumber;
        this.annualIncomeAmount = annualIncomeAmount;
        this.occupation = occupation;
        this.email = email;
        this.signatureBase64 = signatureBase64;
        this.address = address;

        this.validateSelf();
    }

    public static ReadIntegratedApplicationDetailResult of(
            String firstName,
            String lastName,
            String birth,
            String gender,
            String nationality,
            String telePhoneNumber,
            String cellPhoneNumber,
            Boolean isAccredited,
            String schoolName,
            String schoolPhoneNumber,
            String newWorkPlaceName,
            String newWorkPlaceRegistrationNumber,
            String newWorkPlacePhoneNumber,
            Integer annualIncomeAmount,
            String occupation,
            String email,
            String signatureBase64,
            AddressResponseDto address
    ) {
        return ReadIntegratedApplicationDetailResult.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birth(birth)
                .gender(gender)
                .nationality(nationality)
                .telePhoneNumber(telePhoneNumber)
                .cellPhoneNumber(cellPhoneNumber)
                .isAccredited(isAccredited)
                .schoolName(schoolName)
                .schoolPhoneNumber(schoolPhoneNumber)
                .newWorkPlaceName(newWorkPlaceName)
                .newWorkPlaceRegistrationNumber(newWorkPlaceRegistrationNumber)
                .newWorkPlacePhoneNumber(newWorkPlacePhoneNumber)
                .annualIncomeAmount(annualIncomeAmount)
                .occupation(occupation)
                .email(email)
                .signatureBase64(signatureBase64)
                .address(address)
                .build();
    }
}
