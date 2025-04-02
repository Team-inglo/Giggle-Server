package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepFillingOutDocumentsUseCase;
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
public class UpdateUserUserOwnerJobPostingStepFillingOutDocumentsService implements UpdateUserUserOwnerJobPostingStepFillingOutDocumentsUseCase {

    private final AccountRepository accountRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final NotificationRepository notificationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingAndOwnerByIdOrElseThrow(userOwnerJobPostingId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // UserOwnerJobPosting의 상태 변경
        userOwnerJobPosting.updateStepFromStepFillingOutDocument();

        // UserOwnerJobPosting 저장
        userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification notification = Notification.builder()
                .message(EKafkaStatus.USER_DOCUMENT_COMPLETED.getMessage())
                .isRead(false)
                .notificationType(ENotificationType.USER)
                .build();
        notificationRepository.save(notification);

        // Notification 발송
        handlePushAlarm(userOwnerJobPosting, notification);
    }

    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */
    private void handlePushAlarm(UserOwnerJobPosting userOwnerJobPosting, Notification notification) {
        List<AccountDevice> accountDevices = accountDeviceRepository.findByAccountId(userOwnerJobPosting.getOwnerInfo().getId());

        if(userOwnerJobPosting.getOwnerInfo().getNotificationAllowed() && !accountDevices.isEmpty()){
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
