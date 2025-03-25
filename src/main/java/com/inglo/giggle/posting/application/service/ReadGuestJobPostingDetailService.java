package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadGuestJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingDetailUseCase;
import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.repository.CompanyImageRepository;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import com.inglo.giggle.posting.repository.PostingWorkDayTimeRepository;
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

        JobPosting jobPosting = jobPostingRepository.findWithOwnerById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<CompanyImage> companyImageList = companyImageRepository.findAllByJobPosting(jobPosting);
        List<PostingWorkDayTime> postingWorkDayTimeList = postingWorkDayTimeRepository.findAllByJobPosting(jobPosting);

        return ReadGuestJobPostingDetailResponseDto.of(
                jobPosting,
                companyImageList,
                postingWorkDayTimeList
        );

    }
}
