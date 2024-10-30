package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserBookMarkOverviewResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserBookMarkOverviewUseCase;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.repository.mysql.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserBookMarkOverviewService implements ReadUserBookMarkOverviewUseCase {

    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserBookMarkOverviewResponseDto execute(UUID accountId, Integer page, Integer size) {

        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        Page<BookMark> bookMarkPage = bookMarkRepository.findWithOwnerAndWorkDaysTimesByUser(user, PageRequest.of(page -1, size));

        return ReadUserBookMarkOverviewResponseDto.of(
                bookMarkPage
        );
    }
}
