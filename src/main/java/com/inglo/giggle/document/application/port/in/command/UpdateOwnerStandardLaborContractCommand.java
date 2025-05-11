package com.inglo.giggle.document.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
public class UpdateOwnerStandardLaborContractCommand extends SelfValidating<UpdateOwnerStandardLaborContractCommand> {

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

    @NotNull(message = "전화번호를 입력해주세요.")
    @Size(min = 10, max = 20, message = "전화번호는 10자 이상 20자 이하로 입력해주세요.")
    private final String phoneNumber;

    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 1, max = 150, message = "이름은 150자 이하로 입력해주세요.")
    private final String name;

    @NotNull(message = "근무 시작일을 입력해주세요.")
    private final LocalDate startDate;

    @NotNull(message = "근무 종료일을 입력해주세요.")
    private final LocalDate endDate;

    @NotNull(message = "주소를 입력해주세요.")
    @Valid
    private final AddressRequestDto address;

    @NotBlank(message = "근무 상세 정보를 입력해주세요.")
    @Size(min = 1, max = 200, message = "근무 상세 정보는 1자 이상 200자 이하로 입력해주세요.")
    private final String description;

    @NotEmpty(message = "근무 시간을 입력해주세요.")
    @Valid
    private final List<WorkDayTime> workDayTimeList;

    @NotNull(message = "주휴일을 입력해주세요.")
    private final Set<String> weeklyLastDays;

    @NotNull(message = "시급을 입력해주세요.")
    @Max(value = 2000000000, message = "시급은 2,000,000,000원 이하로 입력해주세요.")
    private final Integer hourlyRate;

    @Max(value = 2000000000, message = "성과금은 2,000,000,000원 이하로 입력해주세요.")
    private final Integer bonus;

    @Max(value = 2000000000, message = "추가급여는 2,000,000,000원 이하로 입력해주세요.")
    private final Integer additionalSalary;

    @NotNull(message = "wage rate룰 입력해주세요.")
    private final Double wageRate;

    @NotNull(message = "지급일을 입력해주세요.")
    @Max(value = 31, message = "지급일은 31일 이하로 입력해주세요.")
    @Min(value = 1, message = "지급일은 1일 이상으로 입력해주세요.")
    private final Integer paymentDay;

    @NotNull(message = "지급 방법을 입력해주세요.")
    private final String paymentMethod;

    @NotNull(message = "보험을 입력해주세요.")
    private final Set<String> insurance;

    @NotNull(message = "서명을 입력해주세요.")
    private final String signatureBase64;

    @Getter
    public static class WorkDayTime extends SelfValidating<WorkDayTime> {
        @NotNull(message = "요일을 입력해주세요.")
        private final String dayOfWeek;

        @NotNull(message = "근무 시작 시간을 입력해주세요.")
        private final LocalTime workStartTime;

        @NotNull(message = "근무 종료 시간을 입력해주세요.")
        private final LocalTime workEndTime;

        private final LocalTime breakStartTime;

        private final LocalTime breakEndTime;

        public WorkDayTime(
                String dayOfWeek,
                LocalTime workStartTime,
                LocalTime workEndTime,
                LocalTime breakStartTime,
                LocalTime breakEndTime
        ) {
            this.dayOfWeek = dayOfWeek;
            this.workStartTime = workStartTime;
            this.workEndTime = workEndTime;
            this.breakStartTime = breakStartTime;
            this.breakEndTime = breakEndTime;

            this.validateSelf();
        }
    }

    public UpdateOwnerStandardLaborContractCommand(
            UUID accountId,
            Long documentId,
            String companyName,
            String companyRegistrationNumber,
            String phoneNumber,
            String name,
            LocalDate startDate,
            LocalDate endDate,
            AddressRequestDto address,
            String description,
            List<WorkDayTime> workDayTimeList,
            Set<String> weeklyLastDays,
            Integer hourlyRate,
            Integer bonus,
            Integer additionalSalary,
            Double wageRate,
            Integer paymentDay,
            String paymentMethod,
            Set<String> insurance,
            String signatureBase64
    ) {
        this.accountId = accountId;
        this.documentId = documentId;
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.description = description;
        this.workDayTimeList = workDayTimeList;
        this.weeklyLastDays = weeklyLastDays;
        this.hourlyRate = hourlyRate;
        this.bonus = bonus;
        this.additionalSalary = additionalSalary;
        this.wageRate = wageRate;
        this.paymentDay = paymentDay;
        this.paymentMethod = paymentMethod;
        this.insurance = insurance;
        this.signatureBase64 = signatureBase64;

        this.validateSelf();
    }
}
