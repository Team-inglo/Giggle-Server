package com.inglo.giggle.document.appliation.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.appliation.dto.response.ReadUserDocumentSummaryResponseDto;
import com.inglo.giggle.document.appliation.usecase.ReadUserDocumentSummaryUseCase;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationRepository;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserDocumentSummaryUseCaseService implements ReadUserDocumentSummaryUseCase {
    private final UserRepository userRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final IntegratedApplicationRepository integratedApplicationRepository;

    @Override
    public ReadUserDocumentSummaryResponseDto readUserDocumentSummary(UUID accountId, Long userOwnerJobPostingId) {

        // 유저 정보 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // 시간제 취업 허가서 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null);

        // 표준 근로 계약서 조회
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null);

        // 통합 신청서 조회
        IntegratedApplication integratedApplication = integratedApplicationRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null);

        return ReadUserDocumentSummaryResponseDto.of(partTimeEmploymentPermit, standardLaborContract, integratedApplication);
    }
}
