package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.usecase.ConfirmUserDocumentUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.DocumentAggregate;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.persistence.repository.RejectRepository;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmUserDocumentService implements ConfirmUserDocumentUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final RejectRepository rejectRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Document 정보 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // Reject 정보 조회
        List<Reject> rejects = rejectRepository.findAllByDocumentId(documentId);

        // Reject 이 있을 경우 Reject 정보 삭제
        if (!rejects.isEmpty()) {
            rejectRepository.deleteAll(rejects);
        }

        // Document의 DiscriminatorValue에 따라 생성할 파일을 결정
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // Document를 PartTimeEmploymentPermit으로 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // 시간제 취업 허가서 유학생, 고용주 상태 Confirmation으로 업데이트
                partTimeEmploymentPermit.updateStatusByConfirmation();

                // 시간제 취업 허가서 word 파일 생성
                ByteArrayInputStream partTimeEmploymentPermitWordStream = partTimeEmploymentPermit.createPartTimeEmploymentPermitDocxFile();

                // wordFile 업로드
                String partTimeEmploymentPermitWordUrl = s3Util.uploadWordFile(
                        partTimeEmploymentPermitWordStream, discriminatorValue
                );

                // Document의 wordUrl 업데이트
                partTimeEmploymentPermit.updateWordUrl(partTimeEmploymentPermitWordUrl);

                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // 표준근로계약서 유학생, 고용주 상태 Confirmation으로 업데이트
                standardLaborContract.updateStatusByConfirmation();

                // 표준근로계약서 word 파일 생성
                ByteArrayInputStream standardLaborContractWordStream = standardLaborContract.createStandardLaborContractDocxFile();

                // wordFile 업로드
                String standardLaborContractWordUrl = s3Util.uploadWordFile(
                        standardLaborContractWordStream, discriminatorValue
                );

                // Document의 wordUrl 업데이트
                standardLaborContract.updateWordUrl(standardLaborContractWordUrl);

                standardLaborContractRepository.save(standardLaborContract);

                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private DocumentAggregate getDocumentAggregate(Long documentId) {
        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByIdOrElseThrow(document.getId());

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByIdOrElseThrow(document.getId());

        // Reject 조회
        List<Reject> rejects = rejectRepository.findAllByDocumentId(document.getId());

        // DocumentAggregate 생성
        return DocumentAggregate.builder()
                .partTimeEmploymentPermit(partTimeEmploymentPermit)
                .standardLaborContract(standardLaborContract)
                .rejects(rejects)
                .build();
    }

}
