package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.posting.application.usecase.ReadUserBookMarkOverviewUseCase;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.persistence.repository.BookMarkRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserBookMarkOverviewResponseDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserBookMarkOverviewService implements ReadUserBookMarkOverviewUseCase {

    private final LoadAccountPort loadAccountPort;
    private final BookMarkRepository bookMarkRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserBookMarkOverviewResponseDto execute(UUID accountId, Integer page, Integer size) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // 북마크 조회
        Page<BookMark> bookMarkPage = bookMarkRepository.findWithOwnerAndWorkDaysTimesByUser((User) account, PageRequest.of(page -1, size));

        return ReadUserBookMarkOverviewResponseDto.of(
                bookMarkPage
        );
    }
}
