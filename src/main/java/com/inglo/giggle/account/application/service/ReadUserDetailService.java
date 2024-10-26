package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadUserDetailResponseDto;
import com.inglo.giggle.account.application.usecase.ReadUserDetailUseCase;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserDetailService implements ReadUserDetailUseCase {
    private final UserRepository userRepository;
    @Override
    public ReadUserDetailResponseDto execute(UUID accountId) {
        // 유저 정보 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadUserDetailResponseDto.fromEntity(user);
    }

}
