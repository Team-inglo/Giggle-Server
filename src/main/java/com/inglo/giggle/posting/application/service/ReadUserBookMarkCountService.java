package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.application.dto.response.ReadUserBookMarkCountResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserBookMarkCountUseCase;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.repository.BookMarkRepository;
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
public class ReadUserBookMarkCountService implements ReadUserBookMarkCountUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final BookMarkRepository bookMarkRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserBookMarkCountResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // 유저 조회
        User user = (User) account;

        // 북마크 조회
        List<BookMark> bookMarkList = bookMarkRepository.findByUser(user);

        // 북마크 개수 반환
        return ReadUserBookMarkCountResponseDto.of(
                bookMarkList.size()
        );
    }
}
