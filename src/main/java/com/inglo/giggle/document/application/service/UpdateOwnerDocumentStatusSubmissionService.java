package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerDocumentStatusSubmissionCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateOwnerDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.UpdatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.UpdateStandardLaborContractPort;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOwnerDocumentStatusSubmissionService implements UpdateOwnerDocumentStatusSubmissionUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final UpdatePartTimeEmploymentPermitPort updatePartTimeEmploymentPermitPort;
    private final UpdateStandardLaborContractPort updateStandardLaborContractPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    @Transactional
    public void execute(UpdateOwnerDocumentStatusSubmissionCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkOwnerValidation(readAccountRoleResult.getRole());

        // Document 조회
        Document document = loadDocumentPort.loadAllDocumentOrElseThrow(command.getDocumentId());

        // TODO: UOJP 합치기
//        // UserOwnerJobPosting 정보 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 고용주 유효성 체크
//        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // PartTimeEmploymentPermit 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // PartTimeEmploymentPermit 수정 유효성 체크
                partTimeEmploymentPermit.checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation();

                // 유학생 상태 BEFORE_CONFIRMATION , 고용주 상태 SUBMITTED로 변경
                partTimeEmploymentPermit.updateStatusByOwnerSubmission();
                updatePartTimeEmploymentPermitPort.updatePartTimeEmploymentPermit(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // StandardLaborContract 형변환
                StandardLaborContract standardLaborContractEntity = (StandardLaborContract) document;

                // StandardLaborContract 수정 유효성 체크
                standardLaborContractEntity.checkUpdateOrSubmitOwnerStandardLaborContractValidation();

                // 유학생 상태 BEFORE_CONFIRMATION , 고용주 상태 SUBMITTED로 변경
                standardLaborContractEntity.updateStatusByOwnerSubmission();
                updateStandardLaborContractPort.updateStandardLaborContract(standardLaborContractEntity);

                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private void checkOwnerValidation(ESecurityRole role) {
        // 계정 타입이 OWNER 가 아닐 경우 예외처리
        if (role != ESecurityRole.OWNER) {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }

}
