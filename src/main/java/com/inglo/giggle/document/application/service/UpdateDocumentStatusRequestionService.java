package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.UpdateDocumentStatusReqeustionRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateDocumentStatusRequestionUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.service.RejectService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.RejectRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDocumentStatusRequestionService implements UpdateDocumentStatusRequestionUseCase {
    private final DocumentRepository documentRepository;
    private final RejectRepository rejectRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final StandardLaborContractService standardLaborContractService;
    private final RejectService rejectService;

    @Override
    @Transactional
    public void updateDocumentStatusRequestion(Long documentId, UpdateDocumentStatusReqeustionRequestDto requestDto) {
        // Document 정보 조회
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));


        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // Document를 PartTimeEmploymentPermit으로 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // employee status를 REQUEST로 변경
                partTimeEmploymentPermitService.updateEmployeeStatusRequest(partTimeEmploymentPermit);

                // employer status를 REWRITING으로 변경
                partTimeEmploymentPermitService.updateEmployerStatusRewriting(partTimeEmploymentPermit);

                // Reject 생성
                rejectRepository.save(rejectService.createReject(partTimeEmploymentPermit, requestDto.reason()));
                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // employee status를 REQUEST로 변경
                standardLaborContractService.updateEmployeeStatusRequest(standardLaborContract);

                // employer status를 REWRITING으로 변경
                standardLaborContractService.updateEmployerStatusRewriting(standardLaborContract);

                // Reject 생성
                rejectRepository.save(rejectService.createReject(standardLaborContract, requestDto.reason()));
                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
    }
}
