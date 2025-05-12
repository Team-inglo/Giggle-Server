package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.presentation.dto.response.ReadJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingDetailUseCase;
import com.inglo.giggle.posting.persistence.repository.CompanyImageRepository;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.persistence.repository.PostingWorkDayTimeRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadJobPostingDetailService implements ReadJobPostingDetailUseCase {


    private final LoadAccountPort loadAccountPort;
    private final JobPostingRepository jobPostingRepository;
    private final CompanyImageRepository companyImageRepository;
    private final PostingWorkDayTimeRepository postingWorkDayTimeRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadJobPostingDetailResponseDto execute(UUID accountId, Long jobPostingId) {

        // Account 조회
        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId);

        // 공고조회
        JobPosting jobPosting = jobPostingRepository.findWithOwnerByIdOrElseThrow(jobPostingId);

        boolean isMyPost = jobPosting.getOwnerInfo().getId().equals(accountId);

        // 회사이미지, 근무시간 조회
        List<CompanyImage> companyImageEntityList = companyImageRepository.findAllByJobPosting(jobPosting);
        List<PostingWorkDayTime> postingWorkDayTimeEntityList = postingWorkDayTimeRepository.findAllByJobPosting(jobPosting);

        return ReadJobPostingDetailResponseDto.of(
                account,
                jobPosting,
                companyImageEntityList,
                postingWorkDayTimeEntityList,
                isMyPost
        );

    }
}
