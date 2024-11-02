package com.inglo.giggle.document.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadPartTimeEmploymentPermitDetailResponseDto extends SelfValidating<ReadPartTimeEmploymentPermitDetailResponseDto> {

    @JsonProperty("employee_information")
    private final EmployeeInformationDto employeeInformation;

    @JsonProperty("employer_information")
    private final EmployerInformationDto employerInformation;

    @Builder
    public ReadPartTimeEmploymentPermitDetailResponseDto(
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

        @JsonProperty("major")
        private final String major;

        @JsonProperty("term_of_completion")
        private final Integer termOfCompletion;

        @JsonProperty("phone_number")
        private final String phoneNumber;

        @JsonProperty("email")
        private final String email;

        @Builder
        public EmployeeInformationDto(String firstName, String lastName, String major, Integer termOfCompletion, String phoneNumber, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.major = major;
            this.termOfCompletion = termOfCompletion;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public static EmployeeInformationDto fromEntity(PartTimeEmploymentPermit partTimeEmploymentPermit) {
            return EmployeeInformationDto.builder()
                    .firstName(partTimeEmploymentPermit.getEmployeeFirstName())
                    .lastName(partTimeEmploymentPermit.getEmployeeLastName())
                    .major(partTimeEmploymentPermit.getMajor())
                    .termOfCompletion(partTimeEmploymentPermit.getTermOfCompletion())
                    .phoneNumber(partTimeEmploymentPermit.getEmployeePhoneNumber())
                    .email(partTimeEmploymentPermit.getEmployeeEmail())
                    .build();
        }
    }

    @Getter
    public static class EmployerInformationDto {

        @JsonProperty("company_name")
        private final String companyName;

        @JsonProperty("company_registration_number")
        private final String companyRegistrationNumber;

        @JsonProperty("job_type")
        private final String jobType;

        @JsonProperty("name")
        private final String name;

        @JsonProperty("phone_number")
        private final String phoneNumber;

        @JsonProperty("signature_base64")
        private final String signatureBase64;

        @JsonProperty("work_period")
        private final String workPeriod;

        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @JsonProperty("work_days_weekdays")
        private final String workDaysWeekdays;

        @JsonProperty("work_days_weekends")
        private final String workDaysWeekends;

        @JsonProperty("address")
        private final AddressRequestDto address;

        @Builder
        public EmployerInformationDto(String companyName, String companyRegistrationNumber, String jobType, String name, String phoneNumber,
                                      String signatureBase64, String workPeriod, Integer hourlyRate, String workDaysWeekdays,
                                      String workDaysWeekends, AddressRequestDto address) {
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
        }

        public static EmployerInformationDto fromEntity(PartTimeEmploymentPermit partTimeEmploymentPermit) {
            if (partTimeEmploymentPermit.getCompanyName() == null) {
                return null;
            }
            return EmployerInformationDto.builder()
                    .companyName(partTimeEmploymentPermit.getCompanyName())
                    .companyRegistrationNumber(partTimeEmploymentPermit.getCompanyRegistrationNumber())
                    .jobType(partTimeEmploymentPermit.getJobType())
                    .name(partTimeEmploymentPermit.getEmployerName())
                    .phoneNumber(partTimeEmploymentPermit.getEmployerPhoneNumber())
                    .signatureBase64(partTimeEmploymentPermit.getEmployerSignatureBase64())
                    .workPeriod(partTimeEmploymentPermit.getWorkPeriod().getEnName())
                    .hourlyRate(partTimeEmploymentPermit.getHourlyRate())
                    .workDaysWeekdays(partTimeEmploymentPermit.getWorkDaysWeekDays())
                    .workDaysWeekends(partTimeEmploymentPermit.getWorkDaysWeekends())
                    .address(AddressRequestDto.builder()
                            .addressName(partTimeEmploymentPermit.getEmployerAddress().getAddressName())
                            .region1DepthName(partTimeEmploymentPermit.getEmployerAddress().getRegion1DepthName())
                            .region2DepthName(partTimeEmploymentPermit.getEmployerAddress().getRegion2DepthName())
                            .region3DepthName(partTimeEmploymentPermit.getEmployerAddress().getRegion3DepthName())
                            .region4DepthName(partTimeEmploymentPermit.getEmployerAddress().getRegion4DepthName())
                            .addressDetail(partTimeEmploymentPermit.getEmployerAddress().getAddressDetail())
                            .longitude(partTimeEmploymentPermit.getEmployerAddress().getLongitude())
                            .latitude(partTimeEmploymentPermit.getEmployerAddress().getLatitude())
                            .build())
                    .build();
        }
    }
    public static ReadPartTimeEmploymentPermitDetailResponseDto fromEntity(PartTimeEmploymentPermit partTimeEmploymentPermit) {
        return ReadPartTimeEmploymentPermitDetailResponseDto.builder()
                .employeeInformation(EmployeeInformationDto.fromEntity(partTimeEmploymentPermit))
                .employerInformation(EmployerInformationDto.fromEntity(partTimeEmploymentPermit))
                .build();
    }
}
