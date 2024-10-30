package com.inglo.giggle.document.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.UpdateOwnerDocumentStatusSubmission;
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
public class UpdateOwnerDocumentStatusService implements UpdateOwnerDocumentStatusSubmission {
    private final OwnerRepository ownerRepository;
    private final DocumentRepository documentRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final StandardLaborContractService standardLaborContractService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId) {

        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // 유학생 상태 BEFORE_CONFIRMATION , 고용주 상태 SUBMITTED로 변경 (유학생 수정 불가, 고용주 작성 및 수정 가능)
                partTimeEmploymentPermit =
                        partTimeEmploymentPermitService.updateStatusByOwnerSubmission(partTimeEmploymentPermit);
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                break;
            case "STANDARD_LABOR_CONTRACT":
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;
                standardLaborContract =
                        standardLaborContractService.updateStatusByOwnerSubmission(standardLaborContract);

                standardLaborContractRepository.save(standardLaborContract);
                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

}
