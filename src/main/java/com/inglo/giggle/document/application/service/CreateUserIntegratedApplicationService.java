package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.port.in.command.CreateUserIntegratedApplicationCommand;
import com.inglo.giggle.document.application.port.in.usecase.CreateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.application.port.out.CreateIntegratedApplicationPort;
import com.inglo.giggle.document.application.port.out.LoadIntegratedApplicationPort;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.school.application.port.in.query.ReadSchoolBySchoolNameQuery;
import com.inglo.giggle.school.application.port.in.result.ReadSchoolBySchoolNameResult;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class CreateUserIntegratedApplicationService implements CreateUserIntegratedApplicationUseCase {

    private final LoadIntegratedApplicationPort loadIntegratedApplicationPort;
    private final CreateIntegratedApplicationPort createIntegratedApplicationPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;
    private final ReadSchoolBySchoolNameQuery readSchoolBySchoolNameQuery;
    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(CreateUserIntegratedApplicationCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        //TODO: UOJP 합치기
//        // UserOwnerJobPosting 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // 해당 UserOwnerJobPosting 과 연결된 IntegratedApplication 이 이미 존재하는지 확인
        if (loadIntegratedApplicationPort.loadIntegratedApplicationByUserOwnerJobPostingIdOrElseNull(command.getUserOwnerJobPostingId()) != null) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Address 생성
        Address address = Address.builder()
                .addressName(command.getAddress().addressName())
                .addressDetail(command.getAddress().addressDetail())
                .region1DepthName(command.getAddress().region1DepthName())
                .region2DepthName(command.getAddress().region2DepthName())
                .region3DepthName(command.getAddress().region3DepthName())
                .region4DepthName(command.getAddress().region4DepthName())
                .latitude(command.getAddress().latitude())
                .longitude(command.getAddress().longitude())
                .build();

        // School 조회
        ReadSchoolBySchoolNameResult readSchoolBySchoolNameResult = readSchoolBySchoolNameQuery.execute(command.getSchoolName());
        Long schoolId = readSchoolBySchoolNameResult.getSchoolId();
        String schoolName = readSchoolBySchoolNameResult.getSchoolName();
        String schoolPhoneNumber = readSchoolBySchoolNameResult.getSchoolPhoneNumber();

        // IntegratedApplication 생성
        IntegratedApplication integratedApplication = IntegratedApplication.builder()
                .employeeAddress(address)
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .birth(command.getBirth())
                .gender(EGender.fromString(command.getGender()))
                .nationality(command.getNationality())
                .telePhoneNumber(command.getTelePhoneNumber())
                .cellPhoneNumber(command.getCellPhoneNumber())
                .isAccredited(command.getIsAccredited())
                .newWorkPlaceName(command.getNewWorkPlaceName())
                .newWorkPlaceRegistrationNumber(command.getNewWorkPlaceRegistrationNumber())
                .newWorkPlacePhoneNumber(command.getNewWorkPlacePhoneNumber())
                .annualIncomeAmount(command.getAnnualIncomeAmount())
                .occupation(command.getOccupation())
                .email(command.getEmail())
                .employeeSignatureBase64(command.getSignatureBase64())
                .schoolId(schoolId)
                .build();

        // 통합신청서 유학생 상태 Confirmation으로 업데이트
        integratedApplication.updateEmployeeStatusConfirmation();

        // 통합신청서 word 파일 생성
        ByteArrayInputStream integratedApplicationWordStream = integratedApplication.createIntegratedApplicationDocxFile(schoolName, schoolPhoneNumber);

        // wordFile 업로드
        String integratedApplicationWordUrl = s3Util.uploadWordFile(
                integratedApplicationWordStream, "INTEGRATED_APPLICATION"
        );

        // Document의 wordUrl 업데이트
        integratedApplication.updateWordUrl(integratedApplicationWordUrl);

        createIntegratedApplicationPort.createIntegratedApplication(integratedApplication);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private void checkUserValidation(ESecurityRole role) {
        // 계정 타입이 USER가 아닐 경우 예외처리
        if (role != ESecurityRole.USER) {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }

}
