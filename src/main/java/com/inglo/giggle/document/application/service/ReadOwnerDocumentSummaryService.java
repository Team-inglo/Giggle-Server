package com.inglo.giggle.document.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadOwnerDocumentSummaryResponseDto;
import com.inglo.giggle.document.application.usecase.ReadOwnerDocumentSummaryUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerDocumentSummaryService implements ReadOwnerDocumentSummaryUseCase {
    private final OwnerRepository ownerRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;

    @Override
    public ReadOwnerDocumentSummaryResponseDto readOwnerDocumentSummary(UUID accountId, Long userOwnerJobPostingId) {
        // 고용주 정보 조회
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // 시간제 취업 허가서 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null);

        // 표준 근로 계약서 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null);

        return ReadOwnerDocumentSummaryResponseDto.of(partTimeEmploymentPermit, standardLaborContract);
    }
}
