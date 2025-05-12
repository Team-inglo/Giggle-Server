package com.inglo.giggle.document.adapter.out.persistence.mapper;

import com.inglo.giggle.core.persistence.AddressMapper;
import com.inglo.giggle.document.adapter.out.persistence.entity.PartTimeEmploymentPermitEntity;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartTimeEmploymentPermitMapper {
    public PartTimeEmploymentPermit toDomain(PartTimeEmploymentPermitEntity entity, List<Reject> rejects) {
        if (entity == null) {
            return null;
        }
        return PartTimeEmploymentPermit.builder()
                .id(entity.getId())
                .wordUrl(entity.getWordUrl())
                .userOwnerJobPostingId(entity.getUserOwnerJobPostingId())
                .rejects(rejects)
                .employeeFirstName(entity.getEmployeeFirstName())
                .employeeLastName(entity.getEmployeeLastName())
                .major(entity.getMajor())
                .termOfCompletion(entity.getTermOfCompletion())
                .employeePhoneNumber(entity.getEmployeePhoneNumber())
                .employeeEmail(entity.getEmployeeEmail())
                .employeeStatus(entity.getEmployeeStatus())
                .companyName(entity.getCompanyName())
                .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
                .jobType(entity.getJobType())
                .employerName(entity.getEmployerName())
                .employerPhoneNumber(entity.getEmployerPhoneNumber())
                .employerSignatureBase64(entity.getEmployerSignatureBase64())
                .workPeriod(entity.getWorkPeriod())
                .hourlyRate(entity.getHourlyRate())
                .workDaysWeekDays(entity.getWorkDaysWeekDays())
                .workDaysWeekends(entity.getWorkDaysWeekends())
                .employerStatus(entity.getEmployerStatus())
                .employerAddress(AddressMapper.toDomain(entity.getEmployerAddressEntity()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public PartTimeEmploymentPermit toDomainAlone(PartTimeEmploymentPermitEntity entity) {
        if (entity == null) {
            return null;
        }
        return PartTimeEmploymentPermit.builder()
                .id(entity.getId())
                .wordUrl(entity.getWordUrl())
                .userOwnerJobPostingId(entity.getUserOwnerJobPostingId())
                .employeeFirstName(entity.getEmployeeFirstName())
                .employeeLastName(entity.getEmployeeLastName())
                .major(entity.getMajor())
                .termOfCompletion(entity.getTermOfCompletion())
                .employeePhoneNumber(entity.getEmployeePhoneNumber())
                .employeeEmail(entity.getEmployeeEmail())
                .employeeStatus(entity.getEmployeeStatus())
                .companyName(entity.getCompanyName())
                .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
                .jobType(entity.getJobType())
                .employerName(entity.getEmployerName())
                .employerPhoneNumber(entity.getEmployerPhoneNumber())
                .employerSignatureBase64(entity.getEmployerSignatureBase64())
                .workPeriod(entity.getWorkPeriod())
                .hourlyRate(entity.getHourlyRate())
                .workDaysWeekDays(entity.getWorkDaysWeekDays())
                .workDaysWeekends(entity.getWorkDaysWeekends())
                .employerStatus(entity.getEmployerStatus())
                .employerAddress(AddressMapper.toDomain(entity.getEmployerAddressEntity()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public PartTimeEmploymentPermitEntity toEntity(PartTimeEmploymentPermit domain) {
        if (domain == null) {
            return null;
        }
        return PartTimeEmploymentPermitEntity.builder()
                .id(domain.getId())
                .wordUrl(domain.getWordUrl())
                .userOwnerJobPostingId(domain.getUserOwnerJobPostingId())
                .employeeFirstName(domain.getEmployeeFirstName())
                .employeeLastName(domain.getEmployeeLastName())
                .major(domain.getMajor())
                .termOfCompletion(domain.getTermOfCompletion())
                .employeePhoneNumber(domain.getEmployeePhoneNumber())
                .employeeEmail(domain.getEmployeeEmail())
                .employeeStatus(domain.getEmployeeStatus())
                .companyName(domain.getCompanyName())
                .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
                .jobType(domain.getJobType())
                .employerName(domain.getEmployerName())
                .employerPhoneNumber(domain.getEmployerPhoneNumber())
                .employerSignatureBase64(domain.getEmployerSignatureBase64())
                .workPeriod(domain.getWorkPeriod())
                .hourlyRate(domain.getHourlyRate())
                .workDaysWeekDays(domain.getWorkDaysWeekDays())
                .workDaysWeekends(domain.getWorkDaysWeekends())
                .employerStatus(domain.getEmployerStatus())
                .employerAddressEntity(AddressMapper.toEntity(domain.getEmployerAddress()))
                .build();
    }
}