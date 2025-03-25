package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingBriefListResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserUserOwnerJobPostingBriefListUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserUserOwnerJobPostingBriefListService implements ReadUserUserOwnerJobPostingBriefListUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private static final String UPDATED_AT = "updatedAt";

    @Override
    @Transactional(readOnly = true)
    public ReadUserOwnerJobPostingBriefListResponseDto execute(UUID accountId, Integer page, Integer size) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // 유저 조회
        User user = (User) account;

        // 페이지네이션 설정 UserOwnerJobPosting 조회
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(UPDATED_AT).descending());
        Page<UserOwnerJobPosting> userOwnerJobPostingList = userOwnerJobPostingRepository.findAllPagedWithJobPostingAndOwnerByUser(user, pageable);

        // DTO 반환
        return ReadUserOwnerJobPostingBriefListResponseDto.of(
                userOwnerJobPostingList
        );
    }
}