package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingSummariesUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadJobPostingSummariesService implements ReadJobPostingSummariesUseCase {

    private final AccountRepository accountRepository;
    private final JobPostingRepository jobPostingRepository;

    @Override
    public ReadJobPostingSummariesResponseDto execute(UUID accountId, Long jobPostingId) {

        // 계정 조회
        accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 공고 조회
        JobPosting jobPosting = jobPostingRepository.findWithOwnerById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // DTO 변환
        return ReadJobPostingSummariesResponseDto.fromEntity(
                jobPosting
        );

    }

}
