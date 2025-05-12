package com.inglo.giggle.user.application.service;

import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfNotificationAllowedCommand;
import com.inglo.giggle.user.application.port.in.usecase.UpdateUserSelfNotificationAllowedUseCase;
import com.inglo.giggle.user.application.port.out.LoadUserPort;
import com.inglo.giggle.user.application.port.out.UpdateUserPort;
import com.inglo.giggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserSelfNotificationAllowedService implements UpdateUserSelfNotificationAllowedUseCase {

    private final LoadUserPort loadUserPort;
    private final UpdateUserPort updateUserPort;

    @Override
    @Transactional
    public void execute(UpdateUserSelfNotificationAllowedCommand command) {

        // 계정 정보 조회
        User user = loadUserPort.loadUserOrElseThrow(command.getAccountId());

        // 알림 허용 여부 업데이트
        user.updateNotificationAllowed(command.getIsNotificationAllowed());
        updateUserPort.updateUser(user);
    }

}
