package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.event.dto.NotificationEventDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.usecase.UpdateUserDocumentStatusRequestionUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.persistence.repository.RejectRepository;
import com.inglo.giggle.document.presentation.dto.request.UpdateDocumentStatusReqeustionRequestDto;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserDocumentStatusRequestionService implements UpdateUserDocumentStatusRequestionUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final RejectRepository rejectRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final NotificationRepository notificationRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;


    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateDocumentStatusReqeustionRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Document 정보 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // Document를 PartTimeEmploymentPermit으로 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // PartTimeEmploymentPermit Request 유효성 체크
                partTimeEmploymentPermit.checkRequestPartTimeEmploymentPermitValidation();

                // employee status를 REQUEST로, employer status를 REWRITING으로 변경
                partTimeEmploymentPermit.updateStatusByRequest();
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                // Reject 생성
                rejectRepository.save(Reject.builder()
                        .reason(requestDto.reason())
                        .build());
                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // StandardLaborContract Request 유효성 체크
                standardLaborContract.checkRequestStandardLaborContractValidation();

                // employee status를 REQUEST로 변경
                standardLaborContract.updateStatusByRequest();
                // Reject 생성
                rejectRepository.save(Reject.builder()
                        .reason(requestDto.reason())
                        .build());
                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // Notification 생성 및 저장
        Notification notification = Notification.builder()
                .message(EKafkaStatus.OWNER_DOCUMENT_REQUEST.getMessage())
                .isRead(false)
                .notificationType(ENotificationType.OWNER)
                .build();
        notificationRepository.save(notification);

        // NotificationEvent 발행

        handlePushAlarm(account, userOwnerJobPosting, notification);
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void handlePushAlarm(Account account, UserOwnerJobPosting userOwnerJobPosting, Notification notification) {

        // Owner의 Device Token 목록 조회
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
