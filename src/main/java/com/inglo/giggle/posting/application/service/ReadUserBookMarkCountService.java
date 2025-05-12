package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.posting.application.usecase.ReadUserBookMarkCountUseCase;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.persistence.repository.BookMarkRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserBookMarkCountResponseDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserBookMarkCountService implements ReadUserBookMarkCountUseCase {

    private final LoadAccountPort loadAccountPort;
    private final BookMarkRepository bookMarkRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserBookMarkCountResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // 북마크 조회
        List<BookMark> bookMarks = bookMarkRepository.findByUser((User) account);

        // 북마크 개수 반환
        return ReadUserBookMarkCountResponseDto.of(
                bookMarks.size()
        );
    }
}
