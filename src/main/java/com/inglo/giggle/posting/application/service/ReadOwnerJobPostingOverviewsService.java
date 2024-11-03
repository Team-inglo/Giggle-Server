package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerJobPostingOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadOwnerJobPostingOverviewsService implements ReadOwnerJobPostingOverviewsUseCase {

    private final OwnerRepository ownerRepository;
    private final JobPostingRepository jobPostingRepository;

    private final static String DESCENDING = "DESCENDING";

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerJobPostingOverviewsResponseDto execute(UUID accountId, Integer page, Integer size, String sorting) {

        // 고용주 조회 및 검증
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        Sort sort = sorting.equals(DESCENDING) ?
                Sort.by(Sort.Order.desc("createdAt")) : Sort.by(Sort.Order.asc("createdAt"));

        // 고용주가 등록한 공고 리스트 조회
        Page<JobPosting> jobPostingPageList = jobPostingRepository.findWithPageByOwner(
                owner,
                PageRequest.of(page - 1, size, sort)
        );

        // DTO 반환
        return ReadOwnerJobPostingOverviewsResponseDto.of(
                jobPostingPageList,
                owner
        );
    }
}
