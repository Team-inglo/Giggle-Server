package com.inglo.giggle.document.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.UpdateUserDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.IntegratedApplicationService;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationRepository;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserDocumentStatusSubmissionService implements UpdateUserDocumentStatusSubmissionUseCase {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final StandardLaborContractService standardLaborContractService;
    private final IntegratedApplicationService integratedApplicationService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final IntegratedApplicationRepository integratedApplicationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId) {
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // 유학생 상태 SUBMITTED , 고용주 상태 TEMPORARY_SAVE로 변경 (유학생 수정 불가, 고용주 작성 및 수정 가능)
                partTimeEmploymentPermit =
                        partTimeEmploymentPermitService.updateStatusByUserSubmission(partTimeEmploymentPermit);
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                break;
            case "STANDARD_LABOR_CONTRACT":
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;
                standardLaborContract =
                        standardLaborContractService.updateStatusBySubmission(standardLaborContract);

                standardLaborContractRepository.save(standardLaborContract);
                break;

            case "INTEGRATED_APPLICATION":
                IntegratedApplication integratedApplication = (IntegratedApplication) document;
                integratedApplication =
                        integratedApplicationService.updateStatusBySubmission(integratedApplication);
                integratedApplicationRepository.save(integratedApplication);

                break;
        }

        documentRepository.save(document);
    }
}
