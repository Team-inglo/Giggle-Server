package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationEventService;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.notification.repository.mysql.NotificationRepository;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepApplicationInProgressUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserUserOwnerJobPostingStepApplicationInProgressService implements UpdateUserUserOwnerJobPostingStepApplicationInProgressUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserRepository userRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final NotificationService notificationService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationEventService notificationEventService;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId) {

        // User 조회
        userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting의 상태 변경
        userOwnerJobPostingService.updateStepFromStepApplicationInProgress(userOwnerJobPosting);

        // UserOwnerJobPosting 저장
        userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification userNotification = notificationService.createNotification(
                EKafkaStatus.USER_RESULT.getMessage(),
                userOwnerJobPosting,
                ENotificationType.USER
        );
        Notification ownerNotification = notificationService.createNotification(
                EKafkaStatus.OWNER_COMPLAINT.getMessage(),
                userOwnerJobPosting,
                ENotificationType.OWNER
        );

        notificationRepository.save(userNotification);
        notificationRepository.save(ownerNotification);

        // Notification 발송
        applicationEventPublisher.publishEvent(
                notificationEventService.createNotificationEvent(
                        userOwnerJobPosting.getJobPosting().getTitle(),
                        userNotification.getMessage(),
                        userOwnerJobPosting.getUser().getDeviceToken()
                )
        );

        applicationEventPublisher.publishEvent(
                notificationEventService.createNotificationEvent(
                        userOwnerJobPosting.getJobPosting().getTitle(),
                        ownerNotification.getMessage(),
                        userOwnerJobPosting.getOwner().getDeviceToken()
                )
        );



    }
}
