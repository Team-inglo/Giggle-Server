package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationEventService;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.notification.repository.mysql.NotificationRepository;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase;
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
public class UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewService implements UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final NotificationService notificationService;

    private final OwnerRepository ownerRepository;
    private final NotificationRepository notificationRepository;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationEventService notificationEventService;


    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId) {

        // Owner 조회
        ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

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
        applicationEventPublisher.publishEvent(
                notificationEventService.createNotificationEvent(
                        userOwnerJobPosting.getJobPosting().getTitle(),
                        notification.getMessage(),
                        userOwnerJobPosting.getUser().getDeviceToken()
                )
        );
    }
}
