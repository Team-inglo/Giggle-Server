package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadUserDocumentSummaryResponseDto;
import com.inglo.giggle.document.application.usecase.ReadUserDocumentSummaryUseCase;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.repository.IntegratedApplicationRepository;
import com.inglo.giggle.document.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserDocumentSummaryService implements ReadUserDocumentSummaryUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final IntegratedApplicationRepository integratedApplicationRepository;

    @Override
    public ReadUserDocumentSummaryResponseDto readUserDocumentSummary(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // 시간제 취업 허가서 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 표준 근로 계약서 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 통합 신청서 조회
        IntegratedApplication integratedApplication = integratedApplicationRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        return ReadUserDocumentSummaryResponseDto.of(partTimeEmploymentPermit, standardLaborContract, integratedApplication, userOwnerJobPosting.getStep().getLevel() > 3);
    }

}
