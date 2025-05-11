package com.inglo.giggle.document.persistence.mapper;

import com.inglo.giggle.core.persistence.AddressMapper;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.persistence.entity.IntegratedApplicationEntity;

public class IntegratedApplicationMapper {
    public static IntegratedApplication toDomain(IntegratedApplicationEntity entity) {
        if (entity == null) {
            return null;
        }
        return IntegratedApplication.builder()
                .id(entity.getId())
                .wordUrl(entity.getWordUrl())
                .userOwnerJobPostingId(entity.getUserOwnerJobPostingId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birth(entity.getBirth())
                .gender(entity.getGender())
                .nationality(entity.getNationality())
                .telePhoneNumber(entity.getTelePhoneNumber())
                .cellPhoneNumber(entity.getCellPhoneNumber())
                .isAccredited(entity.getIsAccredited())
                .newWorkPlaceName(entity.getNewWorkPlaceName())
                .newWorkPlaceRegistrationNumber(entity.getNewWorkPlaceRegistrationNumber())
                .newWorkPlacePhoneNumber(entity.getNewWorkPlacePhoneNumber())
                .annualIncomeAmount(entity.getAnnualIncomeAmount())
                .occupation(entity.getOccupation())
                .email(entity.getEmail())
                .employeeSignatureBase64(entity.getEmployeeSignatureBase64())
                .employeeStatus(entity.getEmployeeStatus())
                .employeeAddress(AddressMapper.toDomain(entity.getEmployeeAddressEntity()))
                .schoolId(entity.getSchoolId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static IntegratedApplicationEntity toEntity(IntegratedApplication domain) {
        if (domain == null) {
            return null;
        }
        return IntegratedApplicationEntity.builder()
                .id(domain.getId())
                .wordUrl(domain.getWordUrl())
                .userOwnerJobPostingId(domain.getUserOwnerJobPostingId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .birth(domain.getBirth())
                .gender(domain.getGender())
                .nationality(domain.getNationality())
                .telePhoneNumber(domain.getTelePhoneNumber())
                .cellPhoneNumber(domain.getCellPhoneNumber())
                .isAccredited(domain.getIsAccredited())
                .newWorkPlaceName(domain.getNewWorkPlaceName())
                .newWorkPlaceRegistrationNumber(domain.getNewWorkPlaceRegistrationNumber())
                .newWorkPlacePhoneNumber(domain.getNewWorkPlacePhoneNumber())
                .annualIncomeAmount(domain.getAnnualIncomeAmount())
                .occupation(domain.getOccupation())
                .email(domain.getEmail())
                .employeeSignatureBase64(domain.getEmployeeSignatureBase64())
                .employeeStatus(domain.getEmployeeStatus())
                .employeeAddressEntity(AddressMapper.toEntity(domain.getEmployeeAddress()))
                .schoolId(domain.getSchoolId())
                .build();
    }
}
