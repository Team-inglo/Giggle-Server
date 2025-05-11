package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.usecase.CreateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.persistence.repository.IntegratedApplicationRepository;
import com.inglo.giggle.document.presentation.dto.request.CreateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserIntegratedApplicationService implements CreateUserIntegratedApplicationUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final IntegratedApplicationRepository integratedApplicationRepository;
    private final LoadSchoolPort loadSchoolPort;
    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserIntegratedApplicationRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // 해당 UserOwnerJobPosting 과 연결된 IntegratedApplication 이 이미 존재하는지 확인
        if (integratedApplicationRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId) != null) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

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
        School school = loadSchoolPort.loadSchool(requestDto.schoolName());

        // IntegratedApplication 생성
        IntegratedApplication integratedApplication = IntegratedApplication.builder()
                .employeeAddress(address)
                .firstName(requestDto.firstName())
                .lastName(requestDto.lastName())
                .birth(requestDto.birth())
                .gender(EGender.fromString(requestDto.gender()))
                .nationality(requestDto.nationality())
                .telePhoneNumber(requestDto.telePhoneNumber())
                .cellPhoneNumber(requestDto.cellPhoneNumber())
                .isAccredited(requestDto.isAccredited())
                .newWorkPlaceName(requestDto.newWorkPlaceName())
                .newWorkPlaceRegistrationNumber(requestDto.newWorkPlaceRegistrationNumber())
                .newWorkPlacePhoneNumber(requestDto.newWorkPlacePhoneNumber())
                .annualIncomeAmount(requestDto.annualIncomeAmount())
                .occupation(requestDto.occupation())
                .email(requestDto.email())
                .employeeSignatureBase64(requestDto.signatureBase64())
                .schoolId(school.getId())
                .build();

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
