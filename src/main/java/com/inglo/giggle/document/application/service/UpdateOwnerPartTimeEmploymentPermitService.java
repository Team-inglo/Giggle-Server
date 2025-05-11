package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.usecase.UpdateOwnerPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.presentation.dto.request.UpdateOwnerPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerPartTimeEmploymentPermitService implements UpdateOwnerPartTimeEmploymentPermitUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final NotificationRepository notificationRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 체크
        account.checkOwnerValidation();

        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByIdOrElseThrow(documentId);

        // PartTimeEmploymentPermit 수정 유효성 체크
        partTimeEmploymentPermit.checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation();

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

        // PartTimeEmploymentPermit 수정
        partTimeEmploymentPermit.updateByOwner(
                requestDto.companyName(),
                requestDto.companyRegistrationNumber(),
                requestDto.jobType(),
                requestDto.name(),
                requestDto.phoneNumber(),
                requestDto.signatureBase64(),
                EWorkPeriod.fromString(requestDto.workPeriod()),
                requestDto.hourlyRate(),
                requestDto.workDaysWeekdays(),
                requestDto.workDaysWeekends(),
                address
        );
        partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

        // Notification 생성 및 저장
        Notification notification = Notification.builder()
                .message(EKafkaStatus.USER_PART_TIME_EMPLOYMENT_PERMIT.getMessage())
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

        // User의 Device Token 목록 조회
        UUID userId = userOwnerJobPosting.getUserInfo().getId();
        List<AccountDevice> accountDevices = accountDeviceRepository.findByAccountId(userId);

        // NotificationEvent 생성 및 발행
        if (account.getNotificationAllowed() && !accountDevices.isEmpty()) {
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
