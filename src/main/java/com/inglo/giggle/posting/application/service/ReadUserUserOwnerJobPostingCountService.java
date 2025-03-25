package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingCountResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserUserOwnerJobPostingCountService implements ReadUserUserOwnerJobPostingCountUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    @Transactional(readOnly = true)
    public ReadUserOwnerJobPostingCountResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // 유저 조회
        User user = (User) account;

        // UserOwnerJobPosting 조회
        List<UserOwnerJobPosting> userOwnerJobPostingList = userOwnerJobPostingRepository.findAllWithJobPostingByUser(user);

        // 지원 현황 및 지원 성공 개수 확인하기
        Integer applicationCounts = userOwnerJobPostingList.size();
        Integer successfulHireCounts = userOwnerJobPostingService.getSuccessFulHireCounts(userOwnerJobPostingList);

        // DTO 반환
        return ReadUserOwnerJobPostingCountResponseDto.of(
                applicationCounts,
                successfulHireCounts
        );
    }

}
