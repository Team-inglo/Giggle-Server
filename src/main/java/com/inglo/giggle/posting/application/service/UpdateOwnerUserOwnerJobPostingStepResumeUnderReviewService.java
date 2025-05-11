package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.application.port.out.LoadNotificationPort;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.request.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto;
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
public class UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewService implements UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountDeviceRepository accountDeviceRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final LoadNotificationPort loadNotificationPort;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingAndUserByIdOrElseThrow(userOwnerJobPostingId);

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // UserOwnerJobPosting의 상태 변경 및 실패 시 결과 저장
        userOwnerJobPosting.updateStepFromResumeUnderReview(requestDto.isAccepted());

        // UserOwnerJobPosting 저장
        UserOwnerJobPosting savedUserOwnerJobPosting = userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification notification;
        if(requestDto.isAccepted()){
            notification = Notification.builder()
                    .message(EKafkaStatus.USER_RESUME_CONFIRMED.getMessage())
                    .isRead(false)
                    .notificationType(ENotificationType.USER)
                    .build();
        }else {
            notification = Notification.builder()
                    .message(EKafkaStatus.USER_RESUME_REJECTED.getMessage())
                    .isRead(false)
                    .notificationType(ENotificationType.USER)
                    .build();
        }
        loadNotificationPort.save(notification);

        // NotificationEvent 발행
        handlePushAlarm(account, savedUserOwnerJobPosting, notification);
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void handlePushAlarm(Account account, UserOwnerJobPosting userOwnerJobPosting, Notification notification) {

        // User의 Device Token 목록 조회
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
