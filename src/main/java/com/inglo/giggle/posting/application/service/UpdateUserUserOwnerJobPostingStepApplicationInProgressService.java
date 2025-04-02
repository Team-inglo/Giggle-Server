package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepApplicationInProgressUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.AccountDevice;
import com.inglo.giggle.security.persistence.repository.AccountDeviceRepository;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserUserOwnerJobPostingStepApplicationInProgressService implements UpdateUserUserOwnerJobPostingStepApplicationInProgressUseCase {

    private final AccountRepository accountRepository;
    private final AccountDeviceRepository accountDeviceRepository;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // UserOwnerJobPosting의 상태 변경
        userOwnerJobPosting.updateStepFromStepApplicationInProgress();

        // UserOwnerJobPosting 저장
        userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification userNotification = Notification.builder()
                .message(EKafkaStatus.USER_RESULT.getMessage())
                .isRead(false)
                .notificationType(ENotificationType.USER)
                .build();
        Notification ownerNotification = Notification.builder()
                .message(EKafkaStatus.OWNER_COMPLAINT.getMessage())
                .isRead(false)
                .notificationType(ENotificationType.OWNER)
                .build();

        notificationRepository.save(userNotification);
        notificationRepository.save(ownerNotification);

        // Notification 발송
        handlePushAlarm(
                userOwnerJobPosting,
                userNotification,
                ownerNotification
        );
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void handlePushAlarm(UserOwnerJobPosting userOwnerJobPosting, Notification userNotification, Notification ownerNotification) {

        // User의 Device Token 목록 조회
        List<AccountDevice> userAccountDevices = accountDeviceRepository.findByAccountId(userOwnerJobPosting.getUserInfo().getId());

        if(userOwnerJobPosting.getUserInfo().getNotificationAllowed() && !userAccountDevices.isEmpty()) {

            applicationEventPublisher.publishEvent(
                    NotificationEventDto.of(
                            userOwnerJobPosting.getJobPostingInfo().getTitle(),
                            userNotification.getMessage(),
                            userAccountDevices
                    )
            );
        }

        // Owner의 AccountDevice 목록 조회
        List<AccountDevice> ownerAccountDevices = accountDeviceRepository.findByAccountId(userOwnerJobPosting.getOwnerInfo().getId());

        if(userOwnerJobPosting.getOwnerInfo().getNotificationAllowed() && !ownerAccountDevices.isEmpty()) {
            applicationEventPublisher.publishEvent(
                    NotificationEventDto.of(
                            userOwnerJobPosting.getJobPostingInfo().getTitle(),
                            ownerNotification.getMessage(),
                            ownerAccountDevices
                    )
            );
        }
    }
}
