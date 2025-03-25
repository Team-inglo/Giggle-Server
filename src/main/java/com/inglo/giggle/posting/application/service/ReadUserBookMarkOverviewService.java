package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.application.dto.response.ReadUserBookMarkOverviewResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserBookMarkOverviewUseCase;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.repository.BookMarkRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserBookMarkOverviewService implements ReadUserBookMarkOverviewUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final BookMarkRepository bookMarkRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserBookMarkOverviewResponseDto execute(UUID accountId, Integer page, Integer size) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // 유저 조회
        User user = (User) account;

        // 북마크 조회
        Page<BookMark> bookMarkPage = bookMarkRepository.findWithOwnerAndWorkDaysTimesByUser(user, PageRequest.of(page -1, size));

        return ReadUserBookMarkOverviewResponseDto.of(
                bookMarkPage
        );
    }
}
