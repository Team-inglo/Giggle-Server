package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.notification.repository.mysql.NotificationRepository;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountDeviceRepository;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewService implements UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase {

    private final AccountRepository accountRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final AccountService accountService;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final NotificationService notificationService;

    private final NotificationRepository notificationRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 검사
        accountService.checkOwnerValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // UserOwnerJobPosting의 상태 변경
        userOwnerJobPostingService.updateStepFromStepWaitingForInterview(userOwnerJobPosting);

        // UserOwnerJobPosting 저장
        userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification notification = notificationService.createNotification(
                EKafkaStatus.USER_INTERVIEW_COMPLETED.getMessage(),
                userOwnerJobPosting,
                ENotificationType.USER
        );
        notificationRepository.save(notification);

        // NotificationEvent 생성 및 발행
        handlePushAlarm(account, userOwnerJobPosting, notification);
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void handlePushAlarm(Account account, UserOwnerJobPosting userOwnerJobPosting, Notification notification) {

        // User의 Device Token 목록 조회
        UUID userId = userOwnerJobPosting.getUser().getId();
        List<String> deviceTokens = accountDeviceRepository.findByAccountId(userId).stream()
                .map(AccountDevice::getDeviceToken)
                .toList();

        // NotificationEvent 생성 및 발행
        if(account.getNotificationAllowed() && !deviceTokens.isEmpty()) {
            applicationEventPublisher.publishEvent(
                    NotificationEventDto.of(
                            userOwnerJobPosting.getJobPosting().getTitle(),
                            notification.getMessage(),
                            deviceTokens
                    )
            );
        }
    }
}
