package com.inglo.giggle.document.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EInsurance;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadStandardLaborContractDetailResponseDto extends SelfValidating<ReadStandardLaborContractDetailResponseDto> {

    @JsonProperty("employee_information")
    private final EmployeeInformationDto employeeInformation;

    @JsonProperty("employer_information")
    private final EmployerInformationDto employerInformation;

    @Builder
    public ReadStandardLaborContractDetailResponseDto(
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
        private final AddressRequestDto address;

        @JsonProperty("phone_number")
        private final String phoneNumber;

        @JsonProperty("signature_base64")
        private final String signatureBase64;

        @Builder
        public EmployeeInformationDto(String firstName, String lastName, AddressRequestDto address, String phoneNumber, String signatureBase64) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.signatureBase64 = signatureBase64;
        }

        public static EmployeeInformationDto fromEntity(StandardLaborContract standardLaborContract) {
            return EmployeeInformationDto.builder()
                    .firstName(standardLaborContract.getEmployeeFirstName())
                    .lastName(standardLaborContract.getEmployeeLastName())
                    .address(AddressRequestDto.builder()
                            .addressName(standardLaborContract.getEmployeeAddress().getAddressName())
                            .region1DepthName(standardLaborContract.getEmployeeAddress().getRegion1DepthName())
                            .region2DepthName(standardLaborContract.getEmployeeAddress().getRegion2DepthName())
                            .region3DepthName(standardLaborContract.getEmployeeAddress().getRegion3DepthName())
                            .region4DepthName(standardLaborContract.getEmployeeAddress().getRegion4DepthName())
                            .addressDetail(standardLaborContract.getEmployeeAddress().getAddressDetail())
                            .longitude(standardLaborContract.getEmployeeAddress().getLongitude())
                            .latitude(standardLaborContract.getEmployeeAddress().getLatitude())
                            .build())
                    .phoneNumber(standardLaborContract.getEmployeePhoneNumber())
                    .signatureBase64(standardLaborContract.getEmployeeSignatureBase64())
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
        private final AddressRequestDto address;

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
                                      String startDate, String endDate, AddressRequestDto address, String description,
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

        public static EmployerInformationDto fromEntity(StandardLaborContract standardLaborContract, List<ContractWorkDayTime> contractWorkDayTimes) {
            if (standardLaborContract.getCompanyName() == null) {
                return null;
            }
            return EmployerInformationDto.builder()
                    .companyName(standardLaborContract.getCompanyName())
                    .phoneNumber(standardLaborContract.getEmployerPhoneNumber())
                    .companyRegistrationNumber(standardLaborContract.getCompanyRegistrationNumber())
                    .name(standardLaborContract.getEmployerName())
                    .startDate(standardLaborContract.getStartDate().toString())
                    .endDate(standardLaborContract.getEndDate().toString())
                    .address(AddressRequestDto.builder()
                            .addressName(standardLaborContract.getEmployerAddress().getAddressName())
                            .region1DepthName(standardLaborContract.getEmployerAddress().getRegion1DepthName())
                            .region2DepthName(standardLaborContract.getEmployerAddress().getRegion2DepthName())
                            .region3DepthName(standardLaborContract.getEmployerAddress().getRegion3DepthName())
                            .region4DepthName(standardLaborContract.getEmployerAddress().getRegion4DepthName())
                            .addressDetail(standardLaborContract.getEmployerAddress().getAddressDetail())
                            .longitude(standardLaborContract.getEmployerAddress().getLongitude())
                            .latitude(standardLaborContract.getEmployerAddress().getLatitude())
                            .build())
                    .description(standardLaborContract.getDescription())
                    .workDayTimeList(contractWorkDayTimes.stream().map(workDayTime ->
                            WorkDayTimeDto.builder()
                                    .dayOfWeek(workDayTime.getDayOfWeek().toString())
                                    .workStartTime(workDayTime.getWorkStartTime().toString())
                                    .workEndTime(workDayTime.getWorkEndTime().toString())
                                    .breakStartTime(workDayTime.getBreakStartTime().toString())
                                    .breakEndTime(workDayTime.getBreakEndTime().toString())
                                    .build()
                    ).toList())
                    .weeklyLastDays(standardLaborContract.getWeeklyRestDays().stream().map(EDayOfWeek::toString).toList())
                    .hourlyRate(standardLaborContract.getHourlyRate())
                    .bonus(standardLaborContract.getBonus())
                    .additionalSalary(standardLaborContract.getAdditionalSalary())
                    .wageRate(standardLaborContract.getWageRate())
                    .paymentDay(standardLaborContract.getPaymentDay())
                    .paymentMethod(standardLaborContract.getPaymentMethod().toString())
                    .insurance(standardLaborContract.getInsurances().stream().map(EInsurance::toString).toList())
                    .signatureBase64(standardLaborContract.getEmployerSignatureBase64())
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

            public static WorkDayTimeDto fromEntity(ContractWorkDayTime contractWorkDayTime) {
                return WorkDayTimeDto.builder()
                        .dayOfWeek(contractWorkDayTime.getDayOfWeek().toString())
                        .workStartTime(contractWorkDayTime.getWorkStartTime().toString())
                        .workEndTime(contractWorkDayTime.getWorkEndTime().toString())
                        .breakStartTime(contractWorkDayTime.getBreakStartTime().toString())
                        .breakEndTime(contractWorkDayTime.getBreakEndTime().toString())
                        .build();
            }
        }
    }
    public static ReadStandardLaborContractDetailResponseDto of(
            StandardLaborContract standardLaborContract,
            List<ContractWorkDayTime> contractWorkDayTimes
    ) {
        return ReadStandardLaborContractDetailResponseDto.builder()
                .employeeInformation(EmployeeInformationDto.fromEntity(standardLaborContract))
                .employerInformation(EmployerInformationDto.fromEntity(standardLaborContract, contractWorkDayTimes))
                .build();
    }
}
