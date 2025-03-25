package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.UpdateOwnerDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.DocumentRepository;
import com.inglo.giggle.document.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerDocumentStatusSubmissionService implements UpdateOwnerDocumentStatusSubmissionUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final StandardLaborContractService standardLaborContractService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkOwnerValidation(account);

        // Document 조회
        Document document = documentRepository.findWithUserOwnerJobPostingByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // PartTimeEmploymentPermit 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // PartTimeEmploymentPermit 수정 유효성 체크
                partTimeEmploymentPermitService.checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation(partTimeEmploymentPermit);

                // 유학생 상태 BEFORE_CONFIRMATION , 고용주 상태 SUBMITTED로 변경
                partTimeEmploymentPermit =
                        partTimeEmploymentPermitService.updateStatusByOwnerSubmission(partTimeEmploymentPermit);
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // StandardLaborContract 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // StandardLaborContract 수정 유효성 체크
                standardLaborContractService.checkUpdateOrSubmitOwnerStandardLaborContractValidation(standardLaborContract);

                // 유학생 상태 BEFORE_CONFIRMATION , 고용주 상태 SUBMITTED로 변경
                standardLaborContract =
                        standardLaborContractService.updateStatusByOwnerSubmission(standardLaborContract);
                standardLaborContractRepository.save(standardLaborContract);

                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

}
