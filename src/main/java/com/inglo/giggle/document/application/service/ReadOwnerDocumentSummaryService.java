package com.inglo.giggle.document.application.service;

import com.inglo.giggle.document.application.dto.response.ReadOwnerDocumentSummaryResponseDto;
import com.inglo.giggle.document.application.usecase.ReadOwnerDocumentSummaryUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.RejectRepository;
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
public class ReadOwnerDocumentSummaryService implements ReadOwnerDocumentSummaryUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final RejectRepository rejectRepository;

    @Override
    public ReadOwnerDocumentSummaryResponseDto execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkOwnerValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByIdOrElseThrow(userOwnerJobPostingId);

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // 시간제 취업 허가서 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 표준 근로 계약서 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 거절 사유 조회
        Reject partTimeEmploymentPermitReject = null;
        Reject standardLaborContractReject = null;

        if (partTimeEmploymentPermit != null) {
            partTimeEmploymentPermitReject = rejectRepository.findTopByDocumentIdOrderByCreatedAtDescOrElseNull(partTimeEmploymentPermit.getId());
        }

        if (standardLaborContract != null) {
            standardLaborContractReject = rejectRepository.findTopByDocumentIdOrderByCreatedAtDescOrElseNull(standardLaborContract.getId());
        }

        return ReadOwnerDocumentSummaryResponseDto.of(partTimeEmploymentPermit, standardLaborContract, partTimeEmploymentPermitReject, standardLaborContractReject, userOwnerJobPosting.getStep().getLevel() > 3);
    }
}
