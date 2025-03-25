package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingDetailUseCase;
import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.repository.CompanyImageRepository;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import com.inglo.giggle.posting.repository.PostingWorkDayTimeRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadJobPostingDetailService implements ReadJobPostingDetailUseCase {


    private final AccountRepository accountRepository;
    private final JobPostingRepository jobPostingRepository;
    private final CompanyImageRepository companyImageRepository;
    private final PostingWorkDayTimeRepository postingWorkDayTimeRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadJobPostingDetailResponseDto execute(UUID accountId, Long jobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 공고조회
        JobPosting jobPosting = jobPostingRepository.findWithOwnerById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        boolean isMyPost = jobPosting.getOwner().getId().equals(accountId);

        // 회사이미지, 근무시간 조회
        List<CompanyImage> companyImageList = companyImageRepository.findAllByJobPosting(jobPosting);
        List<PostingWorkDayTime> postingWorkDayTimeList = postingWorkDayTimeRepository.findAllByJobPosting(jobPosting);

        return ReadJobPostingDetailResponseDto.of(
                account,
                jobPosting,
                companyImageList,
                postingWorkDayTimeList,
                isMyPost
        );

    }
}
