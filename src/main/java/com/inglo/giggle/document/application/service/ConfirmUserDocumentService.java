package com.inglo.giggle.document.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.usecase.ConfirmUserDocumentUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.DocumentService;
import com.inglo.giggle.document.domain.service.IntegratedApplicationService;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmUserDocumentService implements ConfirmUserDocumentUseCase {
    private final DocumentRepository documentRepository;
    private final JobPostingRepository jobPostingRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final DocumentService documentService;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final StandardLaborContractService standardLaborContractService;
    private final IntegratedApplicationService integratedApplicationService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void confirmUserDocument(UUID accountId, Long documentId) {

        // Document 정보 조회
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        // User 정보 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Owner 정보 조회
        Owner owner = ownerRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // JobPosting 정보 조회
        JobPosting jobPosting = jobPostingRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Document의 DiscriminatorValue에 따라 생성할 파일을 결정
        String discriminatorValue = document.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (discriminatorValue) {
            case "PART_TIME_EMPLOYMENT_PERMIT":

                // Document를 PartTimeEmploymentPermit으로 형변환
                PartTimeEmploymentPermit partTimeEmploymentPermit = (PartTimeEmploymentPermit) document;

                // 시간제 취업 허가서 유학생 상태 Confirmation으로 업데이트
                partTimeEmploymentPermitService.updateEmployerStatusConfirmation(partTimeEmploymentPermit);

                // 시간제 취업 허가서 고용주 상태 Confirmation으로 업데이트
                partTimeEmploymentPermitService.updateEmployeeStatusConfirmation(partTimeEmploymentPermit);

                // 시간제 취업 허가서 word 파일 생성
                ByteArrayInputStream partTimeEmploymentPermitWordStream = partTimeEmploymentPermitService.createPartTimeEmploymentPermitDocxFile(partTimeEmploymentPermit);

                // wordFile 업로드
                String partTimeEmploymentPermitWordUrl = s3Util.uploadWordFile(
                        partTimeEmploymentPermitWordStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
                );

                // 시간제 취업 허가서 Hwp 파일 생성
                ByteArrayInputStream partTimeEmploymentPermitHwpStream = partTimeEmploymentPermitService.createPartTimeEmploymentPermitHwpFile(partTimeEmploymentPermit);

                // hwpFile 업로드
                String partTimeEmploymentPermitHwpUrl = s3Util.uploadHwpFile(
                        partTimeEmploymentPermitHwpStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
                );

                // Document의 wordUrl, hwpUrl 업데이트
                PartTimeEmploymentPermit updatedPartTimeEmploymentPermit
                        = (PartTimeEmploymentPermit) documentService.updateUrls(partTimeEmploymentPermit, partTimeEmploymentPermitWordUrl, partTimeEmploymentPermitHwpUrl);

                partTimeEmploymentPermitRepository.save(updatedPartTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // 표준근로계약서 유학생 상태 Confirmation으로 업데이트
                standardLaborContractService.updateEmployerStatusConfirmation(standardLaborContract);

                // 표준근로계약서 고용주 상태 Confirmation으로 업데이트
                standardLaborContractService.updateEmployeeStatusConfirmation(standardLaborContract);

                // 표준근로계약서 word 파일 생성
                ByteArrayInputStream standardLaborContractWordStream = standardLaborContractService.createStandardLaborContractDocxFile(standardLaborContract);

                // wordFile 업로드
                String standardLaborContractWordUrl = s3Util.uploadWordFile(
                        standardLaborContractWordStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
                );

                // 표준근로계약서 Hwp 파일 생성
                ByteArrayInputStream standardLaborContractHwpStream = standardLaborContractService.createStandardLaborContractHwpFile(standardLaborContract);

                // hwpFile 업로드
                String standardLaborContractHwpUrl = s3Util.uploadHwpFile(
                        standardLaborContractHwpStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
                );

                // Document의 wordUrl, hwpUrl 업데이트
                StandardLaborContract updatedStandardLaborContract
                        = (StandardLaborContract) documentService.updateUrls(standardLaborContract, standardLaborContractWordUrl, standardLaborContractHwpUrl);

                standardLaborContractRepository.save(updatedStandardLaborContract);
                break;

            case "INTEGRATED_APPLICATION":
                // Document를 IntegratedApplication으로 형변환
                IntegratedApplication integratedApplication = (IntegratedApplication) document;

                // 통합신청서 유학생 상태 Confirmation으로 업데이트
                integratedApplicationService.updateEmployeeStatusConfirmation(integratedApplication);

                // 통합신청서 word 파일 생성
                ByteArrayInputStream integratedApplicationWordStream = integratedApplicationService.createIntegratedApplicationDocxFile(integratedApplication);

                // wordFile 업로드
                String integratedApplicationWordUrl = s3Util.uploadWordFile(
                        integratedApplicationWordStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
                );
                // 통합신청서 hwp 파일 생성
                ByteArrayInputStream integratedApplicationHwpStream = integratedApplicationService.createIntegratedApplicationHwpFile(integratedApplication);

                // hwpFile 업로드
                String integratedApplicationHwpUrl = s3Util.uploadHwpFile(
                        integratedApplicationHwpStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
                );

                // Document의 wordUrl, hwpUrl 업데이트
                IntegratedApplication updatedIntegratedApplication
                        = (IntegratedApplication) documentService.updateUrls(integratedApplication, integratedApplicationWordUrl, integratedApplicationHwpUrl);

                documentRepository.save(updatedIntegratedApplication);
                break;
            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }
}
