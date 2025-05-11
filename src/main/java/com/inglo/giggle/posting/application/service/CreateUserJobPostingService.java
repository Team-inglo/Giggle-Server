package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.application.port.out.LoadNotificationPort;
import com.inglo.giggle.posting.application.usecase.CreateUserJobPostingUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.CreateUserJobPostingResponseDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserJobPostingService implements CreateUserJobPostingUseCase {

    private final LoadAccountPort loadAccountPort;
    private final JobPostingRepository jobPostingRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final LoadNotificationPort loadNotificationPort;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public CreateUserJobPostingResponseDto execute(UUID accountId, Long jobPostingId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        JobPosting jobPosting = jobPostingRepository.findWithOwnerByIdOrElseThrow(jobPostingId);

        // 지원 기간이 지난 공고인지 확인
        if((jobPosting.getRecruitmentDeadLine() != null) && jobPosting.getRecruitmentDeadLine().isBefore(LocalDate.now())) {
            throw new CommonException(ErrorCode.EXPIRED_JOB_POSTING);
        }

        // 유저가 이미 지원한 공고인지 확인
        if(userOwnerJobPostingRepository.existsByUserAndJobPosting((User) account, jobPosting)){
            throw new CommonException(ErrorCode.ALREADY_APPLIED_JOB_POSTING);
        }

        // 유저-공고 매핑 생성
        UserOwnerJobPosting userOwnerJobPosting = UserOwnerJobPosting.builder()
                .step(EApplicationStep.RESUME_UNDER_REVIEW)
                .lastStepUpdated(LocalDate.now())
                .userId(accountId)
                .jobPostingId(jobPostingId)
                .build();

        // 유저-공고 매핑 저장
        UserOwnerJobPosting savedUserOwnerJobPosting = userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification notification = Notification.builder()
                .message(EKafkaStatus.OWNER_NEW_APPLICANT.getMessage())
                .isRead(false)
                .notificationType(ENotificationType.OWNER)
                .build();
        loadNotificationPort.save(notification);

        // NotificationEvent 발행
        handlePushAlarm(account, savedUserOwnerJobPosting, notification);

        // DTO 반환
        return CreateUserJobPostingResponseDto.of(
                savedUserOwnerJobPosting.getId()
        );
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void handlePushAlarm(Account account, UserOwnerJobPosting userOwnerJobPosting, Notification notification) {

        // Owner의 AccountDevice 목록 조회
        UUID ownerId = userOwnerJobPosting.getOwnerInfo().getId();
        List<AccountDevice> accountDevices = accountDeviceRepository.findByAccountId(ownerId);

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
