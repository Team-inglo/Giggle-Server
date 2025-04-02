package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingCountResponseDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerUserOwnerJobPostingCountService implements ReadOwnerUserOwnerJobPostingCountUseCase {

    private final AccountRepository accountRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerUserOwnerJobPostingCountResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // Owner 조회
        Owner owner = (Owner) account;

        // Owner가 등록한 JobPosting 조회
        List<JobPosting> jobPostings = jobPostingRepository.findAllByOwner((Owner) account);

        // Owner가 등록한 UserOwnerJobPosting 조회 및 성공한 지원자 수 확인
        List<UserOwnerJobPosting> userOwnerJobPostings = userOwnerJobPostingRepository.findAllWithJobPostingByOwner((Owner) account);
        Integer successFulHireCounts = userOwnerJobPostingService.getSuccessFulHireCounts(userOwnerJobPostings);

        // DTO 반환
        return ReadOwnerUserOwnerJobPostingCountResponseDto.of(
                jobPostings.size(),
                userOwnerJobPostings.size(),
                successFulHireCounts
        );
    }
}
