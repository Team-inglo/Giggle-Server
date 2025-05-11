package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerPartTimeEmploymentPermitCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateOwnerPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.LoadPartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.UpdatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.notification.application.port.in.command.HandlePushAlarmCommand;
import com.inglo.giggle.notification.application.port.in.usecase.HandlePushAlarmUseCase;
import com.inglo.giggle.owner.application.port.in.query.ReadOwnerDetailQuery;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerDetailResult;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountDetailQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountDetailResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOwnerPartTimeEmploymentPermitService implements UpdateOwnerPartTimeEmploymentPermitUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final LoadPartTimeEmploymentPermitPort loadPartTimeEmploymentPermitPort;
    private final UpdatePartTimeEmploymentPermitPort updatePartTimeEmploymentPermitPort;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final ReadAccountDetailQuery readAccountDetailQuery;
    private final ReadOwnerDetailQuery readOwnerDetailQuery;
    private final HandlePushAlarmUseCase handlePushAlarmUseCase;

    @Override
    @Transactional
    public void execute(UpdateOwnerPartTimeEmploymentPermitCommand command) {

        // Account 조회
        ReadAccountDetailResult readAccountDetailResult = readAccountDetailQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkOwnerValidation(readAccountDetailResult.getRole());

        // Owner 조회
        ReadOwnerDetailResult readOwnerDetailResult = readOwnerDetailQuery.execute(command.getAccountId());

        // Document 조회
        Document document = loadDocumentPort.loadDocument(command.getDocumentId());

        // TODO: UOJP 합치기
        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

//        // UserOwnerJobPosting 고용주 유효성 체크
//        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = loadPartTimeEmploymentPermitPort.loadPartTimeEmploymentPermit(command.getDocumentId());

        // PartTimeEmploymentPermit 수정 유효성 체크
        partTimeEmploymentPermit.checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation();

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

        // PartTimeEmploymentPermit 수정
        partTimeEmploymentPermit.updateByOwner(
                command.getCompanyName(),
                command.getCompanyRegistrationNumber(),
                command.getJobType(),
                command.getName(),
                command.getPhoneNumber(),
                command.getSignatureBase64(),
                EWorkPeriod.fromString(command.getWorkPeriod()),
                command.getHourlyRate(),
                command.getWorkDaysWeekdays(),
                command.getWorkDaysWeekends(),
                address
        );
        updatePartTimeEmploymentPermitPort.updatePartTimeEmploymentPermit(partTimeEmploymentPermit);

        HandlePushAlarmCommand handlePushAlarmCommand = new HandlePushAlarmCommand(
                EKafkaStatus.USER_PART_TIME_EMPLOYMENT_PERMIT,
                ENotificationType.USER,
                readAccountDetailResult.getDeviceTokens(),
                // TODO: Jobposting 합치기
                // userOwnerJobPosting.getJobPosting().getTitle(),
                "",
                readOwnerDetailResult.getNotificationAllowed()
        );

        // Notification 발송
        handlePushAlarmUseCase.execute(handlePushAlarmCommand);
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
