package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.query.ReadUserDocumentSummaryQuery;
import com.inglo.giggle.document.application.port.in.result.ReadUserDocumentSummaryResult;
import com.inglo.giggle.document.application.port.out.LoadIntegratedApplicationPort;
import com.inglo.giggle.document.application.port.out.LoadPartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.LoadStandardLaborContractPort;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserDocumentSummaryService implements ReadUserDocumentSummaryQuery {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final LoadPartTimeEmploymentPermitPort loadPartTimeEmploymentPermitPort;
    private final LoadStandardLaborContractPort loadStandardLaborContractPort;
    private final LoadIntegratedApplicationPort loadIntegratedApplicationPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    public ReadUserDocumentSummaryResult readUserDocumentSummary(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(accountId);

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        // TODO: UOJP 합치기
        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);
//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // 시간제 취업 허가서 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = loadPartTimeEmploymentPermitPort.loadPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 표준 근로 계약서 조회
        StandardLaborContract standardLaborContract = loadStandardLaborContractPort.loadStandardLaborContractByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 통합 신청서 조회
        IntegratedApplication integratedApplication = loadIntegratedApplicationPort.loadIntegratedApplicationByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        ReadUserDocumentSummaryResult.DocumentDetailDto partTimeEmploymentPermitDetailDto = null;
        ReadUserDocumentSummaryResult.DocumentDetailDto standardLaborContractDetailDto = null;
        ReadUserDocumentSummaryResult.SimpleDocumentDto integratedApplicationDetailDto = null;

        if (partTimeEmploymentPermit != null) {
            partTimeEmploymentPermitDetailDto = ReadUserDocumentSummaryResult.DocumentDetailDto.of(
                    partTimeEmploymentPermit.getId(),
                    partTimeEmploymentPermit.getWordUrl(),
                    partTimeEmploymentPermit.getEmployeeStatus()
            );
        }

        if (standardLaborContract != null) {
            standardLaborContractDetailDto = ReadUserDocumentSummaryResult.DocumentDetailDto.of(
                    standardLaborContract.getId(),
                    standardLaborContract.getWordUrl(),
                    standardLaborContract.getEmployeeStatus()
            );
        }

        if (integratedApplication != null) {
            integratedApplicationDetailDto = ReadUserDocumentSummaryResult.SimpleDocumentDto.of(
                    integratedApplication.getId(),
                    integratedApplication.getWordUrl()
            );
        }


        return ReadUserDocumentSummaryResult.of(partTimeEmploymentPermitDetailDto, standardLaborContractDetailDto, integratedApplicationDetailDto, userOwnerJobPosting.getStep().getLevel() > 3);
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
