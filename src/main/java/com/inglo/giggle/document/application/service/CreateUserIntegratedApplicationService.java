package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.dto.request.CreateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.document.application.usecase.CreateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.service.DocumentService;
import com.inglo.giggle.document.domain.service.IntegratedApplicationService;
import com.inglo.giggle.document.repository.DocumentRepository;
import com.inglo.giggle.document.repository.IntegratedApplicationRepository;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.SchoolRepository;
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
public class CreateUserIntegratedApplicationService implements CreateUserIntegratedApplicationUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final IntegratedApplicationRepository integratedApplicationRepository;
    private final SchoolRepository schoolRepository;
    private final IntegratedApplicationService integratedApplicationService;
    private final AddressService addressService;
    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserIntegratedApplicationRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 해당 UserOwnerJobPosting 과 연결된 IntegratedApplication 이 이미 존재하는지 확인
        if (integratedApplicationRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId) != null) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

        // JobPosting 조회
        JobPosting jobPosting =  userOwnerJobPosting.getJobPosting();

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

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
        School school = schoolRepository.findBySchoolNameOrElseThrow(requestDto.schoolName());

        // IntegratedApplication 생성
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

        // 통합신청서 유학생 상태 Confirmation으로 업데이트
        integratedApplicationService.updateEmployeeStatusConfirmation(integratedApplication);

        // 통합신청서 word 파일 생성
        ByteArrayInputStream integratedApplicationWordStream = integratedApplicationService.createIntegratedApplicationDocxFile(integratedApplication);

        // wordFile 업로드
        String integratedApplicationWordUrl = s3Util.uploadWordFile(
                integratedApplicationWordStream, "INTEGRATED_APPLICATION"
        );
//        // 통합신청서 hwp 파일 생성
//        ByteArrayInputStream integratedApplicationHwpStream = integratedApplicationService.createIntegratedApplicationHwpFile(integratedApplication);
//
//        // hwpFile 업로드
//        String integratedApplicationHwpUrl = s3Util.uploadHwpFile(
//                integratedApplicationHwpStream, "INTEGRATED_APPLICATION", jobPosting.getId(), jobPosting.getTitle(), userOwnerJobPosting.getOwner().getOwnerName(), userOwnerJobPosting.getUser().getName()
//        );

        // Document의 wordUrl, hwpUrl 업데이트
        IntegratedApplication updatedIntegratedApplication
                = (IntegratedApplication) documentService.updateUrls(integratedApplication, integratedApplicationWordUrl);
        documentRepository.save(updatedIntegratedApplication);
    }

}
