package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.presentation.dto.response.ReadGuestJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingDetailUseCase;
import com.inglo.giggle.posting.persistence.repository.CompanyImageRepository;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.persistence.repository.PostingWorkDayTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadGuestJobPostingDetailService implements ReadGuestJobPostingDetailUseCase {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyImageRepository companyImageRepository;
    private final PostingWorkDayTimeRepository postingWorkDayTimeRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadGuestJobPostingDetailResponseDto execute(Long jobPostingId) {

        JobPosting jobPosting = jobPostingRepository.findWithOwnerByIdOrElseThrow(jobPostingId);

        List<CompanyImage> companyImages= companyImageRepository.findAllByJobPosting(jobPosting);
        List<PostingWorkDayTime> postingWorkDayTimes = postingWorkDayTimeRepository.findAllByJobPosting(jobPosting);

        return ReadGuestJobPostingDetailResponseDto.of(
                jobPosting,
                companyImages,
                postingWorkDayTimes
        );

    }
}
