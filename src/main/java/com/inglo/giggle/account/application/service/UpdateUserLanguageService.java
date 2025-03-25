package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.request.UpdateUserLanguageRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateUserLanguageUseCase;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.service.UserService;
import com.inglo.giggle.account.repository.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UpdateUserLanguageService implements UpdateUserLanguageUseCase {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserLanguageRequestDto requestDto) {

        // 유저 정보 조회
        User user = userRepository.findByIdOrElseThrow(accountId);

        // 언어 업데이트
        user = userService.updateLanguage(user, requestDto.language());
        userRepository.save(user);
    }

}
