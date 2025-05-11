package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.usecase.UpdateOwnerStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.domain.type.EPaymentMethod;
import com.inglo.giggle.document.persistence.repository.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.document.presentation.dto.request.UpdateOwnerStandardLaborContractRequestDto;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateOwnerStandardLaborContractService implements UpdateOwnerStandardLaborContractUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final ContractWorkDayTimeRepository contractWorkDayTimeRepository;
    private final NotificationRepository notificationRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateOwnerStandardLaborContractRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 체크
        account.checkOwnerValidation();

        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 오너 유효성 체크
        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByIdOrElseThrow(documentId);

        // StandardLaborContract 수정 유효성 체크
        standardLaborContract.checkUpdateOrSubmitOwnerStandardLaborContractValidation();

        // 저장되어있던 ContractWorkDayTime 전부 삭제
        List<ContractWorkDayTime> contractWorkDayTimes = contractWorkDayTimeRepository.findByStandardLaborContractId(documentId);
        contractWorkDayTimeRepository.deleteAll(contractWorkDayTimes);

        // ContractWorkDayTime 생성
        List<ContractWorkDayTime> newContractWorkDayTime = new ArrayList<>();

        requestDto.workDayTimeList().stream()
                .map(workDayTime -> ContractWorkDayTime.builder()
                        .dayOfWeek(EDayOfWeek.fromString(workDayTime.dayOfWeek()))
                        .workStartTime(workDayTime.workStartTime())
                        .workEndTime(workDayTime.workEndTime())
                        .breakStartTime(workDayTime.breakStartTime())
                        .breakEndTime(workDayTime.breakEndTime())
                        .build())
                .forEach(newContractWorkDayTime::add);

        // Address 생성
        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .addressDetail(requestDto.address().addressDetail())
                .region1DepthName(requestDto.address().region1DepthName())
                .region2DepthName(requestDto.address().region2DepthName())
                .region3DepthName(requestDto.address().region3DepthName())
                .region4DepthName(requestDto.address().region4DepthName())
                .latitude(requestDto.address().latitude())
                .longitude(requestDto.address().longitude())
                .build();

        // StandardLaborContract 수정
        standardLaborContract.updateByOwner(
                requestDto.companyName(),
                requestDto.companyRegistrationNumber(),
                requestDto.phoneNumber(),
                requestDto.name(),
                requestDto.startDate(),
                requestDto.endDate(),
                address,
                requestDto.description(),
                requestDto.weeklyLastDays().stream().map(EDayOfWeek::fromString).collect(Collectors.toSet()),
                requestDto.hourlyRate(),
                requestDto.bonus(),
                requestDto.additionalSalary(),
                requestDto.wageRate(),
                requestDto.paymentDay(),
                EPaymentMethod.fromString(requestDto.paymentMethod()),
                requestDto.insurance().stream().map(EInsurance::fromString).collect(Collectors.toSet()),
                requestDto.signatureBase64(),
                newContractWorkDayTime
        );
        standardLaborContractRepository.save(standardLaborContract);

        // Notification 생성 및 저장
        Notification notification = Notification.builder()
                .message(EKafkaStatus.USER_STANDARD_LABOR_CONTRACT.getMessage())
                .isRead(false)
                .notificationType(ENotificationType.USER)
                .build();

        notificationRepository.save(notification);

        // Notification 발송
        handlePushAlarm(account, userOwnerJobPosting, notification);
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void handlePushAlarm(Account account, UserOwnerJobPosting userOwnerJobPosting, Notification notification) {

        // Device Token 목록 조회
        UUID userId = userOwnerJobPosting.getUserInfo().getId();
        List<AccountDevice> accountDevices = accountDeviceRepository.findByAccountId(userId);

        // NotificationEvent 생성 및 발행
        if(account.getNotificationAllowed() && !accountDevices.isEmpty()) {
            applicationEventPublisher.publishEvent(
                    NotificationEventDto.of(
                            userOwnerJobPosting.getJobPostingInfo().getTitle(),
                            notification.getMessage(),
                            accountDevices
                    )
            );
        }
    }
}
