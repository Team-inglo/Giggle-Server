package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.usecase.UpdateUserLanguageUseCase;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.account.persistence.repository.UserRepository;
import com.inglo.giggle.account.presentation.dto.request.UpdateUserLanguageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class UpdateUserLanguageService implements UpdateUserLanguageUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserLanguageRequestDto requestDto) {

        // 유저 정보 조회
        User user = userRepository.findByIdOrElseThrow(accountId);

        // 언어 업데이트
        user.updateLanguage(ELanguage.fromString(requestDto.language()));
        userRepository.save(user);
    }

}
