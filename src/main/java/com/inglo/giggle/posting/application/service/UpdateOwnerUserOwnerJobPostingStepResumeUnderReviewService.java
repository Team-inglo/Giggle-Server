package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationEventService;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.notification.repository.mysql.NotificationRepository;
import com.inglo.giggle.posting.application.dto.request.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewService implements UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationService notificationService;
    private final NotificationEventService notificationEventService;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 검사
        accountService.checkOwnerValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingAndUserById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // UserOwnerJobPosting의 상태 변경 및 실패 시 결과 저장
        userOwnerJobPostingService.updateStepFromResumeUnderReview(
                userOwnerJobPosting,
                requestDto.isAccepted()
        );

        // UserOwnerJobPosting 저장
        UserOwnerJobPosting savedUserOwnerJobPosting = userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification notification;
        if(requestDto.isAccepted()){
            notification = notificationService.createNotification(
                    EKafkaStatus.USER_RESUME_CONFIRMED.getMessage(),
                    savedUserOwnerJobPosting,
                    ENotificationType.USER
            );
        }else {
            notification = notificationService.createNotification(
                    EKafkaStatus.USER_RESUME_REJECTED.getMessage(),
                    savedUserOwnerJobPosting,
                    ENotificationType.USER
            );
        }
        notificationRepository.save(notification);

        // NotificationEvent 발행
        if(account.getNotificationAllowed()){
            applicationEventPublisher.publishEvent(
                    notificationEventService.createNotificationEvent(
                            savedUserOwnerJobPosting.getJobPosting().getTitle(),
                            notification.getMessage(),
                            userOwnerJobPosting.getUser().getDeviceToken()
                    )
            );
        }
    }
}
