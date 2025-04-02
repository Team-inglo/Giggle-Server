package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.usecase.ReadJobPostingSummariesUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadJobPostingSummariesResponseDto;
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
        JobPosting jobPosting = jobPostingRepository.findWithOwnerByIdOrElseThrow(jobPostingId);

        // DTO 변환
        return ReadJobPostingSummariesResponseDto.from(
                jobPosting
        );
    }
}
