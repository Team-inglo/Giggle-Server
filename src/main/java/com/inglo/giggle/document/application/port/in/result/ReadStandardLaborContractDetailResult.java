package com.inglo.giggle.document.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.document.adapter.out.persistence.entity.ContractWorkDayTimeEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadStandardLaborContractDetailResult extends SelfValidating<ReadStandardLaborContractDetailResult> {

    @JsonProperty("employee_information")
    private final EmployeeInformationDto employeeInformation;

    @JsonProperty("employer_information")
    private final EmployerInformationDto employerInformation;

    @Builder
    public ReadStandardLaborContractDetailResult(
            EmployeeInformationDto employeeInformation,
            EmployerInformationDto employerInformation
    ) {
        this.employeeInformation = employeeInformation;
        this.employerInformation = employerInformation;
        this.validateSelf();
    }

    @Getter
    public static class EmployeeInformationDto {

        @JsonProperty("first_name")
        private final String firstName;

        @JsonProperty("last_name")
        private final String lastName;

        @JsonProperty("address")
        private final AddressResponseDto address;

        @JsonProperty("phone_number")
        private final String phoneNumber;

        @JsonProperty("signature_base64")
        private final String signatureBase64;

        @Builder
        public EmployeeInformationDto(String firstName, String lastName, AddressResponseDto address, String phoneNumber, String signatureBase64) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.signatureBase64 = signatureBase64;
        }

        public static EmployeeInformationDto of(String firstName, String lastName, AddressResponseDto address, String phoneNumber, String signatureBase64) {
            return EmployeeInformationDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .address(address)
                    .phoneNumber(phoneNumber)
                    .signatureBase64(signatureBase64)
                    .build();
        }
    }

    @Getter
    public static class EmployerInformationDto {

        @JsonProperty("company_name")
        private final String companyName;

        @JsonProperty("phone_number")
        private final String phoneNumber;

        @JsonProperty("company_registration_number")
        private final String companyRegistrationNumber;

        @JsonProperty("name")
        private final String name;

        @JsonProperty("start_date")
        private final String startDate;

        @JsonProperty("end_date")
        private final String endDate;

        @JsonProperty("address")
        private final AddressResponseDto address;

        @JsonProperty("description")
        private final String description;

        @JsonProperty("work_day_time_list")
        private final List<WorkDayTimeDto> workDayTimeList;

        @JsonProperty("weekly_last_days")
        private final List<String> weeklyLastDays;

        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @JsonProperty("bonus")
        private final Integer bonus;

        @JsonProperty("additional_salary")
        private final Integer additionalSalary;

        @JsonProperty("wage_rate")
        private final double wageRate;

        @JsonProperty("payment_day")
        private final Integer paymentDay;

        @JsonProperty("payment_method")
        private final String paymentMethod;

        @JsonProperty("insurance")
        private final List<String> insurance;

        @JsonProperty("signature_base64")
        private final String signatureBase64;

        @Builder
        public EmployerInformationDto(String companyName, String phoneNumber, String companyRegistrationNumber, String name,
                                      String startDate, String endDate, AddressResponseDto address, String description,
                                      List<WorkDayTimeDto> workDayTimeList, List<String> weeklyLastDays, Integer hourlyRate,
                                      Integer bonus, Integer additionalSalary, double wageRate, Integer paymentDay,
                                      String paymentMethod, List<String> insurance, String signatureBase64) {
            this.companyName = companyName;
            this.phoneNumber = phoneNumber;
            this.companyRegistrationNumber = companyRegistrationNumber;
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
        }

        public static EmployerInformationDto of(String companyName, String phoneNumber, String companyRegistrationNumber, String name,
                                                String startDate, String endDate, AddressResponseDto address, String description,
                                                List<WorkDayTimeDto> workDayTimeList, List<String> weeklyLastDays, Integer hourlyRate,
                                                Integer bonus, Integer additionalSalary, double wageRate, Integer paymentDay,
                                                String paymentMethod, List<String> insurance, String signatureBase64
        ) {
            return EmployerInformationDto.builder()
                    .companyName(companyName)
                    .phoneNumber(phoneNumber)
                    .companyRegistrationNumber(companyRegistrationNumber)
                    .name(name)
                    .startDate(startDate)
                    .endDate(endDate)
                    .address(address)
                    .description(description)
                    .workDayTimeList(workDayTimeList)
                    .weeklyLastDays(weeklyLastDays)
                    .hourlyRate(hourlyRate)
                    .bonus(bonus)
                    .additionalSalary(additionalSalary)
                    .wageRate(wageRate)
                    .paymentDay(paymentDay)
                    .paymentMethod(paymentMethod)
                    .insurance(insurance)
                    .signatureBase64(signatureBase64)
                    .build();
        }

        @Getter
        public static class WorkDayTimeDto {

            @JsonProperty("day_of_week")
            private final String dayOfWeek;

            @JsonProperty("work_start_time")
            private final String workStartTime;

            @JsonProperty("work_end_time")
            private final String workEndTime;

            @JsonProperty("break_start_time")
            private final String breakStartTime;

            @JsonProperty("break_end_time")
            private final String breakEndTime;

            @Builder
            public WorkDayTimeDto(String dayOfWeek, String workStartTime, String workEndTime, String breakStartTime, String breakEndTime) {
                this.dayOfWeek = dayOfWeek;
                this.workStartTime = workStartTime;
                this.workEndTime = workEndTime;
                this.breakStartTime = breakStartTime;
                this.breakEndTime = breakEndTime;
            }

            public static WorkDayTimeDto fromEntity(ContractWorkDayTimeEntity contractWorkDayTimeEntity) {
                return WorkDayTimeDto.builder()
                        .dayOfWeek(contractWorkDayTimeEntity.getDayOfWeek().toString())
                        .workStartTime(DateTimeUtil.convertLocalTimeToString(contractWorkDayTimeEntity.getWorkStartTime()))
                        .workEndTime(DateTimeUtil.convertLocalTimeToString(contractWorkDayTimeEntity.getWorkEndTime()))
                        .breakStartTime(DateTimeUtil.convertLocalTimeToString(contractWorkDayTimeEntity.getBreakStartTime()))
                        .breakEndTime(DateTimeUtil.convertLocalTimeToString(contractWorkDayTimeEntity.getBreakEndTime()))
                        .build();
            }
        }
    }
    public static ReadStandardLaborContractDetailResult of(
            EmployeeInformationDto employeeInformation,
            EmployerInformationDto employerInformation
    ) {
        return ReadStandardLaborContractDetailResult.builder()
                .employeeInformation(employeeInformation)
                .employerInformation(employerInformation)
                .build();
    }
}
