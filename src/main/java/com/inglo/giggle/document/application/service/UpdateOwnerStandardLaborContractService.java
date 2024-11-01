package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateOwnerStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.ContractWorkDayTimeService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.domain.type.EPaymentMethod;
import com.inglo.giggle.document.repository.mysql.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationEventService;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.notification.repository.mysql.NotificationRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateOwnerStandardLaborContractService implements UpdateOwnerStandardLaborContractUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final ContractWorkDayTimeRepository contractWorkDayTimeRepository;
    private final StandardLaborContractService standardLaborContractService;
    private final AddressService addressService;
    private final ContractWorkDayTimeService contractWorkDayTimeService;
    private final NotificationService notificationService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationEventService notificationEventService;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateOwnerStandardLaborContractRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkOwnerValidation(account);

        // Document 조회
        Document document = documentRepository.findWithUserOwnerJobPostingById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 오너 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // StandardLaborContract 수정 유효성 체크
        standardLaborContractService.checkUpdateOrSubmitOwnerStandardLaborContractValidation(standardLaborContract);

        // 저장되어있던 ContractWorkDayTime 전부 삭제
        List<ContractWorkDayTime> contractWorkDayTimes = contractWorkDayTimeRepository.findByStandardLaborContractId(documentId);
        contractWorkDayTimeRepository.deleteAll(contractWorkDayTimes);

        // ContractWorkDayTime 생성
        requestDto.workDayTimeList().stream()
                .map(workDayTime -> contractWorkDayTimeService.createContractWorkDayTime(
                        EDayOfWeek.fromString(workDayTime.dayOfWeek()),
                        workDayTime.workStartTime(),
                        workDayTime.workEndTime(),
                        workDayTime.breakStartTime(),
                        workDayTime.breakEndTime(),
                        standardLaborContract))
                .forEach(contractWorkDayTimeRepository::save);

        // Address 생성
        Address address = addressService.createAddress(
                requestDto.address().addressName(),
                requestDto.address().region1DepthName(),
                requestDto.address().region2DepthName(),
                requestDto.address().region3DepthName(),
                requestDto.address().region4DepthName(),
                requestDto.address().addressDetail(),
                requestDto.address().latitude(),
                requestDto.address().longitude()
        );

        // StandardLaborContract 수정
        StandardLaborContract updatedStandardLaborContract = standardLaborContractService.updateOwnerStandardLaborContract(
                standardLaborContract,
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
                requestDto.signatureBase64()
        );
        standardLaborContractRepository.save(updatedStandardLaborContract);

        // Notification 생성
        Notification notification = notificationService.createNotification(
                EKafkaStatus.USER_STANDARD_LABOR_CONTRACT.getMessage(),
                document.getUserOwnerJobPosting(),
                ENotificationType.USER
        );

        notificationRepository.save(notification);

        // Notification 발송
        applicationEventPublisher.publishEvent(
                notificationEventService.createNotificationEvent(
                        document.getUserOwnerJobPosting().getJobPosting().getTitle(),
                        notification.getMessage(),
                        document.getUserOwnerJobPosting().getUser().getDeviceToken()
                )
        );

    }

}
