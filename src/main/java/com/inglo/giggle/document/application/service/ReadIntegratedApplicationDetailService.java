package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.document.application.port.in.query.ReadIntegratedApplicationDetailQuery;
import com.inglo.giggle.document.application.port.in.result.ReadIntegratedApplicationDetailResult;
import com.inglo.giggle.document.application.port.out.LoadIntegratedApplicationPort;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.school.application.port.in.query.ReadSchoolBySchoolIdQuery;
import com.inglo.giggle.school.application.port.in.result.ReadSchoolBySchoolIdResult;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadIntegratedApplicationDetailService implements ReadIntegratedApplicationDetailQuery {

    private final LoadIntegratedApplicationPort loadIntegratedApplicationPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;
    private final ReadSchoolBySchoolIdQuery readSchoolBySchoolIdQuery;

    @Override
    public ReadIntegratedApplicationDetailResult execute(UUID accountId, Long documentId) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(accountId);

        //TODO: UOJP 합치기
//        UserOwnerJobPosting 정보 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        switch (readAccountRoleResult.getRole()) {
            case USER:

//                // UserOwnerJobPosting 유저 유효성 체크
//                userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

                break;

            case OWNER:

//                // UserOwnerJobPosting 고용주 유효성 체크
//                userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // IntegratedApplication 조회
        IntegratedApplication integratedApplication = loadIntegratedApplicationPort.loadIntegratedApplicationOrElseThrow(documentId);
        ReadSchoolBySchoolIdResult readSchoolBySchoolIdResult = readSchoolBySchoolIdQuery.execute(integratedApplication.getSchoolId());

        return ReadIntegratedApplicationDetailResult.of(
                integratedApplication.getFirstName(),
                integratedApplication.getLastName(),
                DateTimeUtil.convertLocalDateToString(integratedApplication.getBirth()),
                integratedApplication.getGender().name(),
                integratedApplication.getNationality(),
                integratedApplication.getTelePhoneNumber(),
                integratedApplication.getCellPhoneNumber(),
                integratedApplication.getIsAccredited(),
                readSchoolBySchoolIdResult.getSchoolName(),
                readSchoolBySchoolIdResult.getSchoolPhoneNumber(),
                integratedApplication.getNewWorkPlaceName(),
                integratedApplication.getNewWorkPlaceRegistrationNumber(),
                integratedApplication.getNewWorkPlacePhoneNumber(),
                integratedApplication.getAnnualIncomeAmount(),
                integratedApplication.getOccupation(),
                integratedApplication.getEmail(),
                integratedApplication.getEmployeeSignatureBase64(),
                AddressResponseDto.from(integratedApplication.getEmployeeAddress())
        );
    }
}
