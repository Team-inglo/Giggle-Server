package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.UpdateUserDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserDocumentStatusSubmissionService implements UpdateUserDocumentStatusSubmissionUseCase {

    private final AccountRepository accountRepository;
    private final DocumentRepository documentRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Document 타입에 따라 상태 변경
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // PartTimeEmployment 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // 유학생 PartTimeEmploymentPermit 제출 유효성 체크
                partTimeEmploymentPermit.checkUpdateOrSubmitUserPartTimeEmploymentPermitValidation();

                // 유학생 상태 SUBMITTED , 고용주 상태 TEMPORARY_SAVE 로 변경 (유학생 수정 불가, 고용주 작성 및 수정 가능)
                partTimeEmploymentPermit.updateStatusByUserSubmission();
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // StandardLaborContract 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // StandardLaborContract 제출 유효성 체크
                standardLaborContract.checkUpdateOrSubmitUserStandardLaborContractValidation();

                //유학생 상태 SUBMITTED , 고용주 상태 TEMPORARY_SAVE 로 변경 (유학생 수정 불가, 고용주 작성 및 수정 가능)
                standardLaborContract.updateStatusByUserSubmission();
                standardLaborContractRepository.save(standardLaborContract);

                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

}
