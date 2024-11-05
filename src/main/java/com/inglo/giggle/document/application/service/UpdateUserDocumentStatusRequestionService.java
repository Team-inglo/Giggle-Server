package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.document.application.dto.request.UpdateDocumentStatusReqeustionRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateUserDocumentStatusRequestionUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.service.RejectService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.mysql.RejectRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.domain.service.NotificationEventService;
import com.inglo.giggle.notification.domain.service.NotificationService;
import com.inglo.giggle.notification.repository.mysql.NotificationRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserDocumentStatusRequestionService implements UpdateUserDocumentStatusRequestionUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final RejectRepository rejectRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final StandardLaborContractService standardLaborContractService;
    private final RejectService rejectService;
    private final NotificationService notificationService;
    private final NotificationEventService notificationEventService;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateDocumentStatusReqeustionRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Document 정보 조회
        Document document = documentRepository.findWithUserOwnerJobPostingById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // Document를 PartTimeEmploymentPermit으로 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // PartTimeEmploymentPermit Request 유효성 체크
                partTimeEmploymentPermitService.checkRequestPartTimeEmploymentPermitValidation(partTimeEmploymentPermit);

                // employee status를 REQUEST로, employer status를 REWRITING으로 변경
                partTimeEmploymentPermit =
                        partTimeEmploymentPermitService.updateStatusByRequest(partTimeEmploymentPermit);
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                // Reject 생성
                rejectRepository.save(rejectService.createReject(partTimeEmploymentPermit, requestDto.reason()));
                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // StandardLaborContract Request 유효성 체크
                standardLaborContractService.checkRequestStandardLaborContractValidation(standardLaborContract);

                // employee status를 REQUEST로 변경
                standardLaborContract =
                        standardLaborContractService.updateStatusByRequest(standardLaborContract);
                standardLaborContractRepository.save(standardLaborContract);

                // Reject 생성
                rejectRepository.save(rejectService.createReject(standardLaborContract, requestDto.reason()));
                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // Notification 생성 및 저장
        Notification notification = notificationService.createNotification(
                EKafkaStatus.OWNER_DOCUMENT_REQUEST.getMessage(),
                document.getUserOwnerJobPosting(),
                ENotificationType.OWNER
        );
        notificationRepository.save(notification);

        // NotificationEvent 발행
        if(account.getNotificationAllowed()) {
            notificationEventService.createNotificationEvent(
                    document.getUserOwnerJobPosting().getJobPosting().getTitle(),
                    notification.getMessage(),
                    document.getUserOwnerJobPosting().getOwner().getDeviceToken()
            );
        }
    }

}
