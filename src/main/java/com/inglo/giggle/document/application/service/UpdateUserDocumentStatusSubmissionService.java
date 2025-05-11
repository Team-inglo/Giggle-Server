package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.command.UpdateUserDocumentStatusSubmissionCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserDocumentStatusSubmissionUseCase;
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
public class UpdateUserDocumentStatusSubmissionService implements UpdateUserDocumentStatusSubmissionUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final UpdatePartTimeEmploymentPermitPort updatePartTimeEmploymentPermitPort;
    private final UpdateStandardLaborContractPort updateStandardLaborContractPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    @Transactional
    public void execute(UpdateUserDocumentStatusSubmissionCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        // Document 조회
        Document document = loadDocumentPort.loadDocument(command.getDocumentId());

        // UserOwnerJobPosting 정보 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // TODO: UOJP 합치기
//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // PartTimeEmployment 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // 유학생 PartTimeEmploymentPermit 제출 유효성 체크
                partTimeEmploymentPermit.checkUpdateOrSubmitUserPartTimeEmploymentPermitValidation();

                // 유학생 상태 SUBMITTED , 고용주 상태 TEMPORARY_SAVE 로 변경 (유학생 수정 불가, 고용주 작성 및 수정 가능)
                partTimeEmploymentPermit.updateStatusByUserSubmission();
                updatePartTimeEmploymentPermitPort.updatePartTimeEmploymentPermit(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // StandardLaborContract 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // StandardLaborContract 제출 유효성 체크
                standardLaborContract.checkUpdateOrSubmitUserStandardLaborContractValidation();

                //유학생 상태 SUBMITTED , 고용주 상태 TEMPORARY_SAVE 로 변경 (유학생 수정 불가, 고용주 작성 및 수정 가능)
                standardLaborContract.updateStatusByUserSubmission();
                updateStandardLaborContractPort.updateStandardLaborContract(standardLaborContract);

                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
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
