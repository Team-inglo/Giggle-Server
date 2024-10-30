package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.document.application.dto.request.UpdateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.service.IntegratedApplicationService;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserIntegratedApplicationService implements UpdateUserIntegratedApplicationUseCase {

    private final IntegratedApplicationRepository integratedApplicationRepository;
    private final SchoolRepository schoolRepository;
    private final IntegratedApplicationService integratedApplicationService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(Long documentId, UpdateUserIntegratedApplicationRequestDto requestDto) {
        IntegratedApplication integratedApplication = integratedApplicationRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!integratedApplication.getEmployeeStatus().equals(EEmployeeStatus.TEMPORARY_SAVE)){
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        Address address = addressService.createAddress(
                requestDto.address().addressName(),
                requestDto.address().region1DepthName(),
                requestDto.address().region2DepthName(),
                requestDto.address().region3DepthName(),
                requestDto.address().region4DepthName(),
                requestDto.address().addressDetail(),
                requestDto.address().latitude(),
                requestDto.address().longitude()
        );

        School school = schoolRepository.findBySchoolName(requestDto.schoolName())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        IntegratedApplication updatedIntegratedApplication = integratedApplicationService.updateUserIntegratedApplication(
                integratedApplication,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.birth(),
                EGender.fromString(requestDto.gender()),
                requestDto.nationality(),
                requestDto.telePhoneNumber(),
                requestDto.cellPhoneNumber(),
                requestDto.isAccredited(),
                requestDto.newWorkPlaceName(),
                requestDto.newWorkPlaceRegistrationNumber(),
                requestDto.newWorkPlacePhoneNumber(),
                requestDto.annualIncomeAmount(),
                requestDto.occupation(),
                requestDto.email(),
                requestDto.signatureBase64(),
                school,
                address
        );

        integratedApplicationRepository.save(updatedIntegratedApplication);
    }
}
