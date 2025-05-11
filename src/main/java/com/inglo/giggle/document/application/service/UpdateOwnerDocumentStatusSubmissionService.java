package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.UpdateOwnerDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerDocumentStatusSubmissionService implements UpdateOwnerDocumentStatusSubmissionUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 체크
        account.checkOwnerValidation();

        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // PartTimeEmploymentPermit 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // PartTimeEmploymentPermit 수정 유효성 체크
                partTimeEmploymentPermit.checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation();

                // 유학생 상태 BEFORE_CONFIRMATION , 고용주 상태 SUBMITTED로 변경
                partTimeEmploymentPermit.updateStatusByOwnerSubmission();
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // StandardLaborContract 형변환
                StandardLaborContract standardLaborContractEntity = (StandardLaborContract) document;

                // StandardLaborContract 수정 유효성 체크
                standardLaborContractEntity.checkUpdateOrSubmitOwnerStandardLaborContractValidation();

                // 유학생 상태 BEFORE_CONFIRMATION , 고용주 상태 SUBMITTED로 변경
                standardLaborContractEntity.updateStatusByOwnerSubmission();
                standardLaborContractRepository.save(standardLaborContractEntity);

                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

}
