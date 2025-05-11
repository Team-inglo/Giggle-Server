package com.inglo.giggle.document.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateOwnerPartTimeEmploymentPermitCommand extends SelfValidating<UpdateOwnerPartTimeEmploymentPermitCommand> {

    @NotNull(message = "계정 ID는 필수입니다.")
    private final UUID accountId;

    @NotNull(message = "문서 ID는 필수입니다.")
    private final Long documentId;

    @NotNull(message = "회사/점포명을 입력해주세요.")
    @Size(min = 1, max = 150, message = "회사/점포명은 150자 이하로 입력해주세요.")
    private final String companyName;

    @NotNull(message = "사업자 등록번호를 입력해주세요.")
    @Size(min = 1, max = 12, message = "사업자 등록번호는 12자 이하로 입력해주세요.")
    private final String companyRegistrationNumber;

    @NotNull(message = "직종을 입력해주세요.")
    @Size(min = 1, max = 20, message = "직종은 20자 이하로 입력해주세요.")
    private final String jobType;

    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 1, max = 100, message = "이름은 100자 이하로 입력해주세요.")
    private final String name;

    @NotNull(message = "전화번호를 입력해주세요.")
    @Size(min = 10, max = 20, message = "전화번호는 10자 이상 20자 이하로 입력해주세요.")
    private final String phoneNumber;

    @NotNull(message = "서명을 입력해주세요.")
    private final String signatureBase64;

    @NotNull(message = "근무기간을 입력해주세요.")
    private final String workPeriod;

    @NotNull(message = "시급을 입력해주세요.")
    @Max(value = 2000000000, message = "시급은 2,000,000,000원 이하로 입력해주세요.")
    private final Integer hourlyRate;

    @NotNull(message = "평일 근무시간을 입력해주세요.")
    @Size(min = 1, max = 150, message = "평일 근무시간은 150자 이하로 입력해주세요.")
    private final String workDaysWeekdays;

    @NotNull(message = "주말 근무시간을 입력해주세요.")
    @Size(min = 1, max = 150, message = "주말 근무시간은 150자 이하로 입력해주세요.")
    private final String workDaysWeekends;

    @NotNull(message = "주소를 입력해주세요.")
    @Valid
    private final AddressRequestDto address;

    public UpdateOwnerPartTimeEmploymentPermitCommand(
            UUID accountId,
            Long documentId,
            String companyName,
            String companyRegistrationNumber,
            String jobType,
            String name,
            String phoneNumber,
            String signatureBase64,
            String workPeriod,
            Integer hourlyRate,
            String workDaysWeekdays,
            String workDaysWeekends,
            AddressRequestDto address) {
        this.accountId = accountId;
        this.documentId = documentId;
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.jobType = jobType;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.signatureBase64 = signatureBase64;
        this.workPeriod = workPeriod;
        this.hourlyRate = hourlyRate;
        this.workDaysWeekdays = workDaysWeekdays;
        this.workDaysWeekends = workDaysWeekends;
        this.address = address;

        this.validateSelf();
    }
}
