package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.query.ReadOwnerDocumentSummaryQuery;
import com.inglo.giggle.document.application.port.in.result.ReadOwnerDocumentSummaryResult;
import com.inglo.giggle.document.application.port.out.LoadPartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.LoadStandardLaborContractPort;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerDocumentSummaryService implements ReadOwnerDocumentSummaryQuery {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final LoadPartTimeEmploymentPermitPort loadPartTimeEmploymentPermitPort;
    private final LoadStandardLaborContractPort loadStandardLaborContractPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    public ReadOwnerDocumentSummaryResult execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(accountId);

        // 계정 타입 유효성 체크
        checkOwnerValidation(readAccountRoleResult.getRole());

        //TODO: UOJP 합치기
//        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByIdOrElseThrow(userOwnerJobPostingId);
//
//        // UserOwnerJobPosting 고용주 유효성 체크
//        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // 시간제 취업 허가서 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = loadPartTimeEmploymentPermitPort.loadAllPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 표준 근로 계약서 조회
        StandardLaborContract standardLaborContract = loadStandardLaborContractPort.loadStandardLaborContractWithRejectsByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 거절 사유 조회
        Reject partTimeEmploymentPermitReject= null;
        Reject standardLaborContractReject = null;

        if (partTimeEmploymentPermit != null) {
            // 생성 일자가 가장 최근의 것을 가져옴
            partTimeEmploymentPermitReject = partTimeEmploymentPermit.getRejects().stream()
                    .max(Comparator.comparing(BaseDomain::getCreatedAt))
                    .orElse(null);
        }

        if (standardLaborContract != null) {
            standardLaborContractReject = standardLaborContract.getRejects().stream()
                    .max(Comparator.comparing(BaseDomain::getCreatedAt))
                    .orElse(null);
        }

        ReadOwnerDocumentSummaryResult.DocumentDetailDto partTimeEmploymentPermitDetailDto;
        ReadOwnerDocumentSummaryResult.DocumentDetailDto standardLaborContractDetailDto;

        if (partTimeEmploymentPermit == null) {
            partTimeEmploymentPermitDetailDto = null;
        } else {
            partTimeEmploymentPermitDetailDto = ReadOwnerDocumentSummaryResult.DocumentDetailDto.of(
                    partTimeEmploymentPermit.getId(),
                    partTimeEmploymentPermit.getWordUrl(),
                    partTimeEmploymentPermit.getEmployerStatus() != null ? partTimeEmploymentPermit.getEmployerStatus() : null,
                    partTimeEmploymentPermitReject != null ? partTimeEmploymentPermitReject.getReason() : null);
        }

        if (standardLaborContract == null) {
            standardLaborContractDetailDto = null;
        } else {
            standardLaborContractDetailDto = ReadOwnerDocumentSummaryResult.DocumentDetailDto.of(
                    standardLaborContract.getId(),
                    standardLaborContract.getWordUrl(),
                    standardLaborContract.getEmployerStatus() != null ? standardLaborContract.getEmployerStatus() : null,
                    standardLaborContractReject != null ? standardLaborContractReject.getReason() : null);
        }

        return ReadOwnerDocumentSummaryResult.of(partTimeEmploymentPermitDetailDto, standardLaborContractDetailDto, userOwnerJobPosting.getStep().getLevel() > 3);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private void checkOwnerValidation(ESecurityRole role) {
        // 계정 타입이 USER가 아닐 경우 예외처리
        if (role != ESecurityRole.OWNER) {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }
}
