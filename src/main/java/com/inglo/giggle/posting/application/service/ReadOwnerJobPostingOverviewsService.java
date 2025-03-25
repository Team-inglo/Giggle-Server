package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerJobPostingOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
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

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final JobPostingRepository jobPostingRepository;

    private final static String DESCENDING = "DESCENDING";

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerJobPostingOverviewsResponseDto execute(UUID accountId, Integer page, Integer size, String sorting) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkOwnerValidation(account);

        // 고용주 조회
        Owner owner = (Owner) account;

        // 정렬 방향 설정
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
