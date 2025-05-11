package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.port.in.command.UpdateDocumentStatusRequestCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserDocumentStatusRequestUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.UpdatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.UpdateRejectPort;
import com.inglo.giggle.document.application.port.out.UpdateStandardLaborContractPort;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.notification.application.port.in.command.HandlePushAlarmCommand;
import com.inglo.giggle.notification.application.port.in.usecase.HandlePushAlarmUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountDetailQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountDetailResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.user.application.port.in.query.ReadUserDetailQuery;
import com.inglo.giggle.user.application.port.in.result.ReadUserDetailResult;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserDocumentStatusRequestService implements UpdateUserDocumentStatusRequestUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final UpdatePartTimeEmploymentPermitPort updatePartTimeEmploymentPermitPort;
    private final UpdateStandardLaborContractPort updateStandardLaborContractPort;
    private final UpdateRejectPort updateRejectPort;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final ReadAccountDetailQuery readAccountDetailQuery;
    private final ReadUserDetailQuery readUserDetailQuery;
    private final HandlePushAlarmUseCase handlePushAlarmUseCase;

    @Override
    @Transactional
    public void execute(UpdateDocumentStatusRequestCommand command) {

        // Account 조회
        ReadAccountDetailResult readAccountDetailResult = readAccountDetailQuery.execute(command.getAccountId());

        // User 조회
        ReadUserDetailResult readUserDetailResult = readUserDetailQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkUserValidation(readAccountDetailResult.getRole());

        // Document 정보 조회
        Document document = loadDocumentPort.loadDocument(command.getDocumentId());

        // TODO: UOJP 합치기
        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

//        // UserOwnerJobPosting 유저 유효성 체크
//        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // Document를 PartTimeEmploymentPermit으로 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // PartTimeEmploymentPermit Request 유효성 체크
                partTimeEmploymentPermit.checkRequestPartTimeEmploymentPermitValidation();

                // employee status를 REQUEST로, employer status를 REWRITING으로 변경
                partTimeEmploymentPermit.updateStatusByRequest();
                updatePartTimeEmploymentPermitPort.updatePartTimeEmploymentPermit(partTimeEmploymentPermit);

                // Reject 생성
                partTimeEmploymentPermit.getRejects().add(
                        Reject.builder()
                                .reason(command.getReason())
                                .build()
                );
                updateRejectPort.updateReject(partTimeEmploymentPermit);
                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // StandardLaborContract Request 유효성 체크
                standardLaborContract.checkRequestStandardLaborContractValidation();

                // employee status를 REQUEST로 변경
                standardLaborContract.updateStatusByRequest();
                updateStandardLaborContractPort.updateStandardLaborContract(standardLaborContract);

                // Reject 생성
                standardLaborContract.getRejects().add(
                        Reject.builder()
                                .reason(command.getReason())
                                .build()
                );
                updateRejectPort.updateReject(standardLaborContract);
                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        HandlePushAlarmCommand handlePushAlarmCommand = new HandlePushAlarmCommand(
                EKafkaStatus.OWNER_DOCUMENT_REQUEST,
                ENotificationType.OWNER,
                readAccountDetailResult.getDeviceTokens(),
                // TODO: Jobposting 합치기
                // userOwnerJobPosting.getJobPosting().getTitle(),
                "",
                readUserDetailResult.getNotificationAllowed()
        );

        // Notification 발송
        handlePushAlarmUseCase.execute(handlePushAlarmCommand);
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
