package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.port.in.command.UpdateUserIntegratedApplicationCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.LoadIntegratedApplicationPort;
import com.inglo.giggle.document.application.port.out.UpdateIntegratedApplicationPort;
import com.inglo.giggle.document.domain.Document;
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
public class UpdateUserIntegratedApplicationService implements UpdateUserIntegratedApplicationUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final LoadIntegratedApplicationPort loadIntegratedApplicationPort;
    private final UpdateIntegratedApplicationPort updateIntegratedApplicationPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;
    private final ReadSchoolBySchoolNameQuery readSchoolBySchoolNameQuery;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UpdateUserIntegratedApplicationCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        // Document 조회
        Document document = loadDocumentPort.loadDocument(command.getDocumentId());

        //TODO: UOJP 합치기
//        // UserOwnerJobPosting 정보 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);
//
//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // IntegratedApplication 조회
        IntegratedApplication integratedApplication = loadIntegratedApplicationPort.loadIntegratedApplication(command.getDocumentId());

        // IntegratedApplication 수정 유효성 체크
        integratedApplication.checkUpdateOrSubmitUserIntegratedApplicationValidation();

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

        // IntegratedApplication 수정
        integratedApplication.updateByUser(
                command.getFirstName(),
                command.getLastName(),
                command.getBirth(),
                command.getGender(),
                command.getNationality(),
                command.getTelePhoneNumber(),
                command.getCellPhoneNumber(),
                command.getIsAccredited(),
                command.getNewWorkPlaceName(),
                command.getNewWorkPlaceRegistrationNumber(),
                command.getNewWorkPlacePhoneNumber(),
                command.getAnnualIncomeAmount(),
                command.getOccupation(),
                command.getEmail(),
                command.getSignatureBase64(),
                address,
                schoolId
        );

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
        updateIntegratedApplicationPort.updateIntegratedApplication(integratedApplication);
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
