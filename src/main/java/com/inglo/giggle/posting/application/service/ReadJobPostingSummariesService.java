package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingSummariesUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadJobPostingSummariesService implements ReadJobPostingSummariesUseCase {

    private final JobPostingRepository jobPostingRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadJobPostingSummariesResponseDto execute(Long jobPostingId) {

        // 공고 조회
        JobPosting jobPosting = jobPostingRepository.findWithOwnerById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // DTO 변환
        return ReadJobPostingSummariesResponseDto.fromEntity(
                jobPosting
        );
    }
}
