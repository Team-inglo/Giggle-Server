package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserBookMarkCountResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserBookMarkCountUseCase;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.repository.mysql.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserBookMarkCountService implements ReadUserBookMarkCountUseCase {

    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserBookMarkCountResponseDto execute(UUID accountId) {

        // 계정 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 북마크 조회
        List<BookMark> bookMarkList = bookMarkRepository.findByUser(user);

        // 북마크 개수 반환
        return ReadUserBookMarkCountResponseDto.of(
                bookMarkList.size()
        );
    }
}
