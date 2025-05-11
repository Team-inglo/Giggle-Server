package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.port.in.command.ConfirmUserDocumentCommand;
import com.inglo.giggle.document.application.port.in.usecase.ConfirmUserDocumentUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.LoadRejectPort;
import com.inglo.giggle.document.application.port.out.UpdatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.UpdateRejectPort;
import com.inglo.giggle.document.application.port.out.UpdateStandardLaborContractPort;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmUserDocumentService implements ConfirmUserDocumentUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final UpdatePartTimeEmploymentPermitPort updatePartTimeEmploymentPermitPort;
    private final UpdateStandardLaborContractPort updateStandardLaborContractPort;
    private final LoadRejectPort loadRejectPort;
    private final UpdateRejectPort updateRejectPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(ConfirmUserDocumentCommand command) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountRoleResult.getRole());

        // Document 정보 조회
        Document document = loadDocumentPort.loadDocument(command.getDocumentId());

        //TODO: UOJP 합치기
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);
//
//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Reject 정보 조회
        List<Reject> rejects = loadRejectPort.loadRejects(command.getDocumentId());

        // Reject 이 있을 경우 Reject 정보 삭제
        if (!rejects.isEmpty()) {
            document.deleteAllRejects();
            updateRejectPort.updateReject(document);
        }

        // Document의 DiscriminatorValue에 따라 생성할 파일을 결정
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // Document를 PartTimeEmploymentPermit으로 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // 시간제 취업 허가서 유학생, 고용주 상태 Confirmation으로 업데이트
                partTimeEmploymentPermit.updateStatusByConfirmation();

                // 시간제 취업 허가서 word 파일 생성
                ByteArrayInputStream partTimeEmploymentPermitWordStream = partTimeEmploymentPermit.createPartTimeEmploymentPermitDocxFile();

                // wordFile 업로드
                String partTimeEmploymentPermitWordUrl = s3Util.uploadWordFile(
                        partTimeEmploymentPermitWordStream, discriminatorValue
                );

                // Document의 wordUrl 업데이트
                partTimeEmploymentPermit.updateWordUrl(partTimeEmploymentPermitWordUrl);

                updatePartTimeEmploymentPermitPort.updatePartTimeEmploymentPermit(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // 표준근로계약서 유학생, 고용주 상태 Confirmation으로 업데이트
                standardLaborContract.updateStatusByConfirmation();

                // 표준근로계약서 word 파일 생성
                ByteArrayInputStream standardLaborContractWordStream = standardLaborContract.createStandardLaborContractDocxFile();

                // wordFile 업로드
                String standardLaborContractWordUrl = s3Util.uploadWordFile(
                        standardLaborContractWordStream, discriminatorValue
                );

                // Document의 wordUrl 업데이트
                standardLaborContract.updateWordUrl(standardLaborContractWordUrl);

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
