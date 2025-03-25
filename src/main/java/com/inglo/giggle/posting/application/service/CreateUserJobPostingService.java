package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.notification.repository.NotificationRepository;
import com.inglo.giggle.posting.application.dto.response.CreateUserJobPostingResponseDto;
import com.inglo.giggle.posting.application.usecase.CreateUserJobPostingUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.AccountDevice;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountDeviceRepository;
import com.inglo.giggle.security.repository.AccountRepository;
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

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final JobPostingRepository jobPostingRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final NotificationService notificationService;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public CreateUserJobPostingResponseDto execute(UUID accountId, Long jobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // 유저, 공고 조회
        User user = (User) account;

        JobPosting jobPosting = jobPostingRepository.findWithOwnerById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 지원 기간이 지난 공고인지 확인
        if((jobPosting.getRecruitmentDeadLine() != null) && jobPosting.getRecruitmentDeadLine().isBefore(LocalDate.now())) {
            throw new CommonException(ErrorCode.EXPIRED_JOB_POSTING);
        }

        // 유저가 이미 지원한 공고인지 확인
        if(userOwnerJobPostingRepository.existsByUserAndJobPosting(user, jobPosting)){
            throw new CommonException(ErrorCode.ALREADY_APPLIED_JOB_POSTING);
        }

        // 유저-공고 매핑 생성
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingService.createUserOwnerJobPosting(
                user,
                jobPosting,
                jobPosting.getOwner()
        );

        // 유저-공고 매핑 저장
        UserOwnerJobPosting savedUserOwnerJobPosting = userOwnerJobPostingRepository.saveAndReturn(userOwnerJobPosting);

        // Notification 생성 및 저장
        Notification notification = notificationService.createNotification(
                EKafkaStatus.OWNER_NEW_APPLICANT.getMessage(),
                userOwnerJobPosting,
                ENotificationType.OWNER
        );
        notificationRepository.save(notification);

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
        UUID ownerId = userOwnerJobPosting.getOwner().getId();
        List<AccountDevice> accountDevices = accountDeviceRepository.findByAccountId(ownerId);

        // NotificationEvent 생성 및 발행
        if(account.getNotificationAllowed() && !accountDevices.isEmpty()) {
            applicationEventPublisher.publishEvent(
                    NotificationEventDto.of(
                            userOwnerJobPosting.getJobPosting().getTitle(),
                            notification.getMessage(),
                            accountDevices
                    )
            );
        }
    }
}
