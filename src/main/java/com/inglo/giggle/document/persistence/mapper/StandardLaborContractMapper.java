package com.inglo.giggle.document.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.persistence.entity.StandardLaborContractEntity;

import java.util.EnumSet;

public class StandardLaborContractMapper {
    public static StandardLaborContract toDomain(StandardLaborContractEntity entity) {
        if (entity == null) {
            return null;
        }
        return StandardLaborContract.builder()
                .id(entity.getId())
                .wordUrl(entity.getWordUrl())
                .userOwnerJobPostingId(entity.getUserOwnerJobPostingId())
                .employeeFirstName(entity.getEmployeeFirstName())
                .employeeLastName(entity.getEmployeeLastName())
                .employeePhoneNumber(entity.getEmployeePhoneNumber())
                .employeeSignatureBase64(entity.getEmployeeSignatureBase64())
                .employeeStatus(entity.getEmployeeStatus())
                .employeeAddress(AddressMapper.toDomain(entity.getEmployeeAddressEntity()))
                .companyName(entity.getCompanyName())
                .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
                .employerName(entity.getEmployerName())
                .employerPhoneNumber(entity.getEmployerPhoneNumber())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .employerAddress(AddressMapper.toDomain(entity.getEmployerAddressEntity()))
                .description(entity.getDescription())
                .weeklyRestDays(entity.getWeeklyRestDays())
                .hourlyRate(entity.getHourlyRate())
                .bonus(entity.getBonus())
                .additionalSalary(entity.getAdditionalSalary())
                .wageRate(entity.getWageRate())
                .paymentDay(entity.getPaymentDay())
                .paymentMethod(entity.getPaymentMethod())
                .insurances(entity.getInsurances())
                .employerSignatureBase64(entity.getEmployerSignatureBase64())
                .employerStatus(entity.getEmployerStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static StandardLaborContractEntity toEntity(StandardLaborContract domain) {
        if (domain == null) {
            return null;
        }
        return StandardLaborContractEntity.builder()
                .id(domain.getId())
                .wordUrl(domain.getWordUrl())
                .userOwnerJobPostingId(domain.getUserOwnerJobPostingId())
                .employeeFirstName(domain.getEmployeeFirstName())
                .employeeLastName(domain.getEmployeeLastName())
                .employeePhoneNumber(domain.getEmployeePhoneNumber())
                .employeeSignatureBase64(domain.getEmployeeSignatureBase64())
                .employeeStatus(domain.getEmployeeStatus())
                .employeeAddressEntity(AddressMapper.toEntity(domain.getEmployeeAddress()))
                .companyName(domain.getCompanyName())
                .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
                .employerName(domain.getEmployerName())
                .employerPhoneNumber(domain.getEmployerPhoneNumber())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .employerAddressEntity(AddressMapper.toEntity(domain.getEmployerAddress()))
                .description(domain.getDescription())
                .weeklyRestDays((EnumSet<EDayOfWeek>) domain.getWeeklyRestDays())
                .hourlyRate(domain.getHourlyRate())
                .bonus(domain.getBonus())
                .additionalSalary(domain.getAdditionalSalary())
                .wageRate(domain.getWageRate())
                .paymentDay(domain.getPaymentDay())
                .paymentMethod(domain.getPaymentMethod())
                .insurances((EnumSet<EInsurance>) domain.getInsurances())
                .employerSignatureBase64(domain.getEmployerSignatureBase64())
                .employerStatus(domain.getEmployerStatus())
                .build();
    }
}
