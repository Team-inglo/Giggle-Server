package com.inglo.giggle.document.application.service;

import com.inglo.giggle.document.application.usecase.ReadOwnerDocumentSummaryUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.persistence.repository.RejectRepository;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.document.presentation.dto.response.ReadOwnerDocumentSummaryResponseDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerDocumentSummaryService implements ReadOwnerDocumentSummaryUseCase {

    private final AccountRepository accountRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final RejectRepository rejectRepository;

    @Override
    public ReadOwnerDocumentSummaryResponseDto execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkOwnerValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByIdOrElseThrow(userOwnerJobPostingId);

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // 시간제 취업 허가서 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 표준 근로 계약서 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByUserOwnerJobPostingIdOrElseNull(userOwnerJobPostingId);

        // 거절 사유 조회
        Reject partTimeEmploymentPermitReject= null;
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
