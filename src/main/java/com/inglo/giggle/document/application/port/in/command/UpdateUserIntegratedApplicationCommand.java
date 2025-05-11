package com.inglo.giggle.document.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EGender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class UpdateUserIntegratedApplicationCommand extends SelfValidating<UpdateUserIntegratedApplicationCommand> {

    @NotNull(message = "계정 ID는 필수입니다.")
    private final UUID accountId;

    @NotNull(message = "문서 ID는 필수입니다.")
    private final Long documentId;

    @NotNull(message = "first name 을 입력해주세요.")
    @Size(min = 1, max = 50, message = "first name은 50자 이하로 입력해주세요.")
    private final String firstName;

    @NotNull(message = "last name 을 입력해주세요.")
    @Size(min = 1, max = 100, message = "last name은 100자 이하로 입력해주세요.")
    private final String lastName;

    @NotNull(message = "생년월일을 입력해주세요.")
    private final LocalDate birth;

    @NotNull(message = "성별을 입력해주세요.")
    private final EGender gender;

    @NotNull(message = "국적을 입력해주세요.")
    @Size(min = 1, max = 56, message = "국적은 56자 이하로 입력해주세요.")
    private final String nationality;

    @NotNull(message = "telephone number를 입력해주세요.")
    @Size(min = 10, max = 20, message = "telephone number는 10자 이상 20자 이하로 입력해주세요.")
    private final String telePhoneNumber;

    @NotNull(message = "cell phone number를 입력해주세요.")
    @Size(min = 10, max = 20, message = "cell phone number는 10자 이상 20자 이하로 입력해주세요.")
    private final String cellPhoneNumber;

    @NotNull(message = "인증 대학 여부를 입력해주세요.")
    private final Boolean isAccredited;

    @NotNull(message = "학교명을 입력해주세요.")
    @Size(min = 1, max = 100, message = "학교명은 100자 이하로 입력해주세요.")
    private final String schoolName;

    @NotNull(message = "학교 전화번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "학교 전화번호는 8자 이상 20자 이하로 입력해주세요.")
    private final String schoolPhoneNumber;

    @NotNull(message = "새 근무지 명을 입력해주세요.")
    @Size(min = 1, max = 50, message = "새 근무지 명은 50자 이하로 입력해주세요.")
    private final String newWorkPlaceName;

    @NotNull(message = "새 근무지 사업자 등록번호를 입력해주세요.")
    @Size(min = 1, max = 12, message = "새 근무지 사업자 등록번호는 12자 이하로 입력해주세요.")
    private final String newWorkPlaceRegistrationNumber;

    @NotNull(message = "새 근무지 전화번호를 입력해주세요.")
    @Size(min = 10, max = 20, message = "새 근무지 전화번호는 10자 이상 20자 이하로 입력해주세요.")
    private final String newWorkPlacePhoneNumber;

    @NotNull(message = "연봉을 입력해주세요.")
    @Max(value = 2000000000, message = "연봉은 2,000,000,000원 이하로 입력해주세요.")
    private final Integer annualIncomeAmount;

    @NotNull(message = "현재 직업을 입력해주세요.")
    @Size(min = 1, max = 30, message = "현재 직업은 30자 이하로 입력해주세요.")
    private final String occupation;

    @NotNull(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    @Size(max = 320, message = "이메일은 320자 이하로 입력해주세요.")
    private final String email;

    @NotNull(message = "서명을 입력해주세요.")
    private final String signatureBase64;

    @NotNull(message = "주소를 입력해주세요.")
    @Valid
    private final AddressRequestDto address;

    public UpdateUserIntegratedApplicationCommand(
            UUID accountId,
            Long documentId,
            String firstName,
            String lastName,
            LocalDate birth,
            EGender gender,
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
            AddressRequestDto address
    ) {
        this.accountId = accountId;
        this.documentId = documentId;
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
    }
}
