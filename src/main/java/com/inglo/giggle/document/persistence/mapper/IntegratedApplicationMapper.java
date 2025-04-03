package com.inglo.giggle.document.persistence.mapper;

import com.inglo.giggle.address.persistence.mapper.AddressMapper;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.persistence.entity.IntegratedApplicationEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;

public class IntegratedApplicationMapper {
    public static IntegratedApplication toDomain(IntegratedApplicationEntity entity) {
        if (entity == null) {
            return null;
        }
        return IntegratedApplication.builder()
                .id(entity.getId())
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
                .schoolId(isInitialized(entity.getSchoolEntity())? entity.getSchoolEntity().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .schoolInfo(isInitialized(entity.getSchoolEntity()) ? IntegratedApplication.SchoolInfo.builder()
                        .schoolName(entity.getSchoolEntity().getSchoolName())
                        .schoolPhoneNumber(entity.getSchoolEntity().getSchoolPhoneNumber())
                        .build() : null)
                .build();
    }

    public static IntegratedApplicationEntity toEntity(IntegratedApplication domain) {
        if (domain == null) {
            return null;
        }
        return IntegratedApplicationEntity.builder()
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
                .build();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
