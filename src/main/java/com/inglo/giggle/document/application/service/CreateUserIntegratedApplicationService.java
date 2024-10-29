package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.document.application.dto.request.CreateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.document.application.usecase.CreateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.service.IntegratedApplicationService;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserIntegratedApplicationService implements CreateUserIntegratedApplicationUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final IntegratedApplicationRepository integratedApplicationRepository;
    private final SchoolRepository schoolRepository;
    private final IntegratedApplicationService integratedApplicationService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void execute(Long userOwnerJobPostingId, CreateUserIntegratedApplicationRequestDto requestDto) {
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        Address address = addressService.createAddress(requestDto.address());

        School school = schoolRepository.findBySchoolName(requestDto.schoolName())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        IntegratedApplication integratedApplication = integratedApplicationService.createIntegratedApplication(
                userOwnerJobPosting,
                address,
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
                school
        );

        integratedApplicationRepository.save(integratedApplication);
    }


}
