package com.inglo.giggle.document.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.document.application.usecase.ConfirmUserDocumentUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.DocumentService;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.repository.DocumentRepository;
import com.inglo.giggle.document.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.RejectRepository;
import com.inglo.giggle.document.repository.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
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

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final JobPostingRepository jobPostingRepository;
    private final OwnerRepository ownerRepository;
    private final DocumentService documentService;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;
    private final StandardLaborContractService standardLaborContractService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final RejectRepository rejectRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Document 정보 조회
        Document document = documentRepository.findWithUserOwnerJobPostingByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // User 형변환
        User user = (User) account;

        // Owner 정보 조회
        Owner owner = ownerRepository.findByDocumentIdOrElseThrow(documentId);

        // JobPosting 정보 조회
        JobPosting jobPosting = jobPostingRepository.findByIdOrElseThrow(document.getUserOwnerJobPosting().getJobPosting().getId());

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
                partTimeEmploymentPermit =
                        partTimeEmploymentPermitService.updateStatusByConfirmation(partTimeEmploymentPermit);
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                // 시간제 취업 허가서 word 파일 생성
                ByteArrayInputStream partTimeEmploymentPermitWordStream
                        = partTimeEmploymentPermitService.createPartTimeEmploymentPermitDocxFile(partTimeEmploymentPermit);

                // wordFile 업로드
                String partTimeEmploymentPermitWordUrl = s3Util.uploadWordFile(
                        partTimeEmploymentPermitWordStream, discriminatorValue
                );

//                // 시간제 취업 허가서 Hwp 파일 생성
//                ByteArrayInputStream partTimeEmploymentPermitHwpStream
//                        = partTimeEmploymentPermitService.createPartTimeEmploymentPermitHwpFile(partTimeEmploymentPermit);
//
//                // hwpFile 업로드
//                String partTimeEmploymentPermitHwpUrl = s3Util.uploadHwpFile(
//                        partTimeEmploymentPermitHwpStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
//                );

                // Document의 wordUrl, hwpUrl 업데이트
                partTimeEmploymentPermit
                        = (PartTimeEmploymentPermit) documentService.updateUrls(partTimeEmploymentPermit, partTimeEmploymentPermitWordUrl);
                partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);

                break;

            case "STANDARD_LABOR_CONTRACT":

                // Document를 StandardLaborContract으로 형변환
                StandardLaborContract standardLaborContract = (StandardLaborContract) document;

                // 표준근로계약서 유학생, 고용주 상태 Confirmation으로 업데이트
                standardLaborContract =
                        standardLaborContractService.updateStatusByConfirmation(standardLaborContract);
                standardLaborContractRepository.save(standardLaborContract);

                // 표준근로계약서 word 파일 생성
                ByteArrayInputStream standardLaborContractWordStream = standardLaborContractService.createStandardLaborContractDocxFile(standardLaborContract);

                // wordFile 업로드
                String standardLaborContractWordUrl = s3Util.uploadWordFile(
                        standardLaborContractWordStream, discriminatorValue
                );

//                // 표준근로계약서 Hwp 파일 생성
//                ByteArrayInputStream standardLaborContractHwpStream = standardLaborContractService.createStandardLaborContractHwpFile(standardLaborContract);
//
//                // hwpFile 업로드
//                String standardLaborContractHwpUrl = s3Util.uploadHwpFile(
//                        standardLaborContractHwpStream, discriminatorValue, jobPosting.getId(), jobPosting.getTitle(), owner.getOwnerName(), user.getName()
//                );

                // Document의 wordUrl, hwpUrl 업데이트
                standardLaborContract
                        = (StandardLaborContract) documentService.updateUrls(standardLaborContract, standardLaborContractWordUrl);
                standardLaborContractRepository.save(standardLaborContract);

                break;

            default:
                throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

}
