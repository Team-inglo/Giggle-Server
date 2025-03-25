package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.dto.request.UpdateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.service.DocumentService;
import com.inglo.giggle.document.domain.service.IntegratedApplicationService;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationRepository;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserIntegratedApplicationService implements UpdateUserIntegratedApplicationUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final DocumentService documentService;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final IntegratedApplicationRepository integratedApplicationRepository;
    private final SchoolRepository schoolRepository;
    private final IntegratedApplicationService integratedApplicationService;
    private final AddressService addressService;
    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateUserIntegratedApplicationRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Document 조회
        Document document = documentRepository.findWithUserOwnerJobPostingById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingById(document.getUserOwnerJobPosting().getId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // JobPosting 조회
        JobPosting jobPosting = userOwnerJobPosting.getJobPosting();

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // IntegratedApplication 조회
        IntegratedApplication integratedApplication = integratedApplicationRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // IntegratedApplication 수정 유효성 체크
        integratedApplicationService.checkUpdateOrSubmitUserIntegratedApplicationValidation(integratedApplication);

        // Address 생성
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

        // School 조회
        School school = schoolRepository.findBySchoolName(requestDto.schoolName())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // IntegratedApplication 수정
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

        // 통합신청서 유학생 상태 Confirmation으로 업데이트
        integratedApplicationService.updateEmployeeStatusConfirmation(integratedApplication);

        // 통합신청서 word 파일 생성
        ByteArrayInputStream integratedApplicationWordStream = integratedApplicationService.createIntegratedApplicationDocxFile(integratedApplication);

        // wordFile 업로드
        String integratedApplicationWordUrl = s3Util.uploadWordFile(
                integratedApplicationWordStream, "INTEGRATED_APPLICATION", jobPosting.getId(), jobPosting.getTitle(), userOwnerJobPosting.getOwner().getOwnerName(), userOwnerJobPosting.getUser().getName()
        );

        // Document의 wordUrl, hwpUrl 업데이트
        updatedIntegratedApplication
                = (IntegratedApplication) documentService.updateUrls(integratedApplication, integratedApplicationWordUrl);
        integratedApplicationRepository.save(updatedIntegratedApplication);
    }

}
