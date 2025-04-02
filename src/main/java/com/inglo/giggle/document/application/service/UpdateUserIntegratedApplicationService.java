package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.usecase.UpdateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.IntegratedApplicationRepository;
import com.inglo.giggle.document.presentation.dto.request.UpdateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.repository.SchoolRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserIntegratedApplicationService implements UpdateUserIntegratedApplicationUseCase {

    private final AccountRepository accountRepository;
    private final DocumentRepository documentRepository;
    private final IntegratedApplicationRepository integratedApplicationRepository;
    private final SchoolRepository schoolRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateUserIntegratedApplicationRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // IntegratedApplication 조회
        IntegratedApplication integratedApplication = integratedApplicationRepository.findByIdOrElseThrow(documentId);

        // IntegratedApplication 수정 유효성 체크
        integratedApplication.checkUpdateOrSubmitUserIntegratedApplicationValidation();

        // Address 생성
        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .addressDetail(requestDto.address().addressDetail())
                .region1DepthName(requestDto.address().region1DepthName())
                .region2DepthName(requestDto.address().region2DepthName())
                .region3DepthName(requestDto.address().region3DepthName())
                .region4DepthName(requestDto.address().region4DepthName())
                .latitude(requestDto.address().latitude())
                .longitude(requestDto.address().longitude())
                .build();

        // School 조회
        School school = schoolRepository.findBySchoolNameOrElseThrow(requestDto.schoolName());

        // IntegratedApplication 수정
        integratedApplication.updateByUser(
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
                address,
                school.getId()
        );

        // 통합신청서 유학생 상태 Confirmation으로 업데이트
        integratedApplication.updateEmployeeStatusConfirmation();

        // 통합신청서 word 파일 생성
        ByteArrayInputStream integratedApplicationWordStream = integratedApplication.createIntegratedApplicationDocxFile(school);

        // wordFile 업로드
        String integratedApplicationWordUrl = s3Util.uploadWordFile(
                integratedApplicationWordStream, "INTEGRATED_APPLICATION"
        );

        // Document의 wordUrl 업데이트
        integratedApplication.updateWordUrl(integratedApplicationWordUrl);
        integratedApplicationRepository.save(integratedApplication);
    }

}
