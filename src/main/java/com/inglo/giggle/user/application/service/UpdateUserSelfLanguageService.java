package com.inglo.giggle.user.application.service;

import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfLanguageCommand;
import com.inglo.giggle.user.application.port.in.usecase.UpdateUserSelfLanguageUseCase;
import com.inglo.giggle.user.application.port.out.LoadUserPort;
import com.inglo.giggle.user.application.port.out.UpdateUserPort;
import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.user.domain.type.ELanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserSelfLanguageService implements UpdateUserSelfLanguageUseCase {

    private final LoadUserPort loadUserPort;
    private final UpdateUserPort updateUserPort;

    @Override
    @Transactional
    public void execute(UpdateUserSelfLanguageCommand command) {

        // 유저 정보 조회
        User user = loadUserPort.loadUser(command.getAccountId());

        // 언어 업데이트
        user.updateLanguage(ELanguage.fromString(command.getLanguage()));
        updateUserPort.updateUser(user);
    }

}
