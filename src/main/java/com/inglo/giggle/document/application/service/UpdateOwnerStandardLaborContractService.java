package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerStandardLaborContractCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateOwnerStandardLaborContractUseCase;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.LoadStandardLaborContractPort;
import com.inglo.giggle.document.application.port.out.UpdateContractWorkDayTimePort;
import com.inglo.giggle.document.application.port.out.UpdateStandardLaborContractPort;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.domain.type.EPaymentMethod;
import com.inglo.giggle.notification.application.port.in.command.HandlePushAlarmCommand;
import com.inglo.giggle.notification.application.port.in.usecase.HandlePushAlarmUseCase;
import com.inglo.giggle.owner.application.port.in.query.ReadOwnerDetailQuery;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerDetailResult;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountDetailQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountDetailResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateOwnerStandardLaborContractService implements UpdateOwnerStandardLaborContractUseCase {

    private final LoadDocumentPort loadDocumentPort;
    private final LoadStandardLaborContractPort loadStandardLaborContractPort;
    private final UpdateStandardLaborContractPort updateStandardLaborContractPort;
    private final UpdateContractWorkDayTimePort updateContractWorkDayTimePort;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final ReadAccountDetailQuery readAccountDetailQuery;
    private final ReadOwnerDetailQuery readOwnerDetailQuery;
    private final HandlePushAlarmUseCase handlePushAlarmUseCase;

    @Override
    @Transactional
    public void execute(UpdateOwnerStandardLaborContractCommand command) {

        // Account 조회
        ReadAccountDetailResult readAccountDetailResult = readAccountDetailQuery.execute(command.getAccountId());

        // 계정 타입 유효성 체크
        checkOwnerValidation(readAccountDetailResult.getRole());

        // Owner 조회
        ReadOwnerDetailResult readOwnerDetailResult = readOwnerDetailQuery.execute(command.getAccountId());

        // Document 조회
        Document document = loadDocumentPort.loadAllDocumentOrElseThrow(command.getDocumentId());

        // TODO: UOJP 합치기
        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

//        // UserOwnerJobPosting 고용주 유효성 체크
//        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = loadStandardLaborContractPort.loadAllStandardLaborContractOrElseThrow(command.getDocumentId());

        // StandardLaborContract 수정 유효성 체크
        standardLaborContract.checkUpdateOrSubmitOwnerStandardLaborContractValidation();

        // 저장되어있던 ContractWorkDayTime 전부 삭제
        standardLaborContract.deleteAllContractWorkDayTime();
        updateStandardLaborContractPort.updateStandardLaborContract(standardLaborContract);

        // ContractWorkDayTime 생성
        List<ContractWorkDayTime> newContractWorkDayTime = new ArrayList<>();

        command.getWorkDayTimeList().stream()
                .map(workDayTime -> ContractWorkDayTime.builder()
                        .dayOfWeek(EDayOfWeek.fromString(workDayTime.getDayOfWeek()))
                        .workStartTime(workDayTime.getWorkStartTime())
                        .workEndTime(workDayTime.getWorkEndTime())
                        .breakStartTime(workDayTime.getBreakStartTime())
                        .breakEndTime(workDayTime.getBreakEndTime())
                        .build())
                .forEach(newContractWorkDayTime::add);

        // ContractWorkDayTime 저장
        newContractWorkDayTime.forEach(contractWorkDayTime -> {
            standardLaborContract.getWorkDayTimes().add(contractWorkDayTime);
        });

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

        // StandardLaborContract 수정
        standardLaborContract.updateByOwner(
                command.getCompanyName(),
                command.getCompanyRegistrationNumber(),
                command.getPhoneNumber(),
                command.getName(),
                command.getStartDate(),
                command.getEndDate(),
                address,
                command.getDescription(),
                command.getWeeklyLastDays().stream().map(EDayOfWeek::fromString).collect(Collectors.toSet()),
                command.getHourlyRate(),
                command.getBonus(),
                command.getAdditionalSalary(),
                command.getWageRate(),
                command.getPaymentDay(),
                EPaymentMethod.fromString(command.getPaymentMethod()),
                command.getInsurance().stream().map(EInsurance::fromString).collect(Collectors.toSet()),
                command.getSignatureBase64()
        );

        // StandardLaborContract 저장
        updateContractWorkDayTimePort.updateContractWorkDayTime(standardLaborContract);
        updateStandardLaborContractPort.updateStandardLaborContract(standardLaborContract);

        HandlePushAlarmCommand handlePushAlarmCommand = new HandlePushAlarmCommand(
                EKafkaStatus.USER_STANDARD_LABOR_CONTRACT,
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
